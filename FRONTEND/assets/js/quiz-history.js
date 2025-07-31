document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    const historyContainer = document.getElementById("quizHistoryCardsContainer");
    // historyContainer.innerHTML = "<p>Loading quiz history...</p>";

    fetch(`http://localhost:8080/api/quiz-results/user/${user.userID}`, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch quiz history");
            }
            return response.json();
        })
        .then(data => {
            data.reverse(); // Show the most recent results first
            console.log("Quiz History Data:", data);
            if (data.length === 0) {
                historyContainer.innerHTML = "<p>No quiz results found.</p>";
                return;
            }

            const cardsHtml = data.map(result => `
                <div class="box quiz-card">
                    <h3>${new Date(result.completionTime).toLocaleString()}</h3>
                    <p><strong>Name:</strong> ${result.studentName}</p>
                    <p><strong>Difficulty:</strong> ${result.difficultyLevel}</p>
                    <p><strong>Correct:</strong> ${result.correctAnswers} / ${result.totalQuestions}</p>
                    <p><strong>Score:</strong> ${result.scorePercentage.toFixed(1)}%</p>
                    <a href="results.html" class="button small primary">View Details</a>
                </div>
            `).join("");

            historyContainer.innerHTML = cardsHtml;
        })
        .catch(error => {
            console.error(error);
            historyContainer.innerHTML = "<p>Error loading quiz history.</p>";
        });
});
