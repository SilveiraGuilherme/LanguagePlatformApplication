// quiz-history.js
// Handles display of a student's past quiz results on the quiz history page.

import { getQuizResultsByUserID } from './api-config.js';

// Create a single quiz result card HTML string
function createQuizCard(result) {
    const score = typeof result.scorePercentage === 'number' ? result.scorePercentage.toFixed(1) : 'N/A';
    return `
    <div class="box quiz-card">
        <h3>${new Date(result.completionTime).toLocaleString()}</h3>
        <p><strong>Name:</strong> ${result.studentName}</p>
        <p><strong>Difficulty:</strong> ${result.difficultyLevel}</p>
        <p><strong>Correct:</strong> ${result.correctAnswers} / ${result.totalQuestions}</p>
        <p><strong>Score:</strong> ${score}%</p>
        <a href="results.html" class="button small primary" data-resultid="${result.resultID}">View Details</a>
    </div>
  `;
}

// Load and render quiz history after DOM is ready
document.addEventListener("DOMContentLoaded", async () => {
    const userRaw = localStorage.getItem("user");
    if (!userRaw) {
        window.location.href = "login.html";
        return;
    }

    let user;
    try {
        user = JSON.parse(userRaw);
    } catch {
        window.location.href = "login.html";
        return;
    }

    if (!user?.userID) {
        window.location.href = "login.html";
        return;
    }

    const historyContainer = document.getElementById("quizHistoryCardsContainer");
    if (!historyContainer) {
        console.error("Quiz history container not found.");
        return;
    }

    try {
        const data = await getQuizResultsByUserID(user.userID);

        if (!Array.isArray(data) || data.length === 0) {
            historyContainer.innerHTML = "<p>No quiz results found.</p>";
            return;
        }

        // Remove duplicates based on resultID
        const uniqueResultsMap = new Map();
        data.forEach(result => {
            if (!uniqueResultsMap.has(result.resultID)) {
                uniqueResultsMap.set(result.resultID, result);
            }
        });
        const uniqueResults = Array.from(uniqueResultsMap.values());

        // Render quiz result cards
        uniqueResults.reverse();
        const cardsHtml = uniqueResults.map(createQuizCard).join("");
        historyContainer.innerHTML = cardsHtml;

        // Handle "View Details" button clicks
        historyContainer.querySelectorAll('a[data-resultid]').forEach(a => {
            a.addEventListener('click', (e) => {
                e.preventDefault();
                const rid = a.getAttribute('data-resultid');
                if (rid) {
                    localStorage.setItem('latestResultID', rid);
                    window.location.href = 'results.html';
                }
            });
        });
    } catch (error) {
        console.error("Error loading quiz history:", error);
        historyContainer.innerHTML = "<p>Error loading quiz history.</p>";
    }
});
