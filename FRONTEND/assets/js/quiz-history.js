document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    const historyContainer = document.getElementById("quizHistoryList");
    historyContainer.innerHTML = "<p>Loading quiz history...</p>";

    fetch(`http://localhost:8080/api/quiz-results/${user.userID}`, {
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

            const listHtml = data.map(result => `
                <div class="box">
                    <h3>${new Date(result.dateSubmitted).toLocaleDateString()}</h3>
                    <p>Correct: ${result.correctAnswers} / ${result.totalQuestions}</p>
                    <p>Score: ${(result.correctAnswers / result.totalQuestions * 100).toFixed(1)}%</p>
                </div>
            `).join("");

            historyContainer.innerHTML = listHtml;
        })
        .catch(error => {
            console.error(error);
            historyContainer.innerHTML = "<p>Error loading quiz history.</p>";
        });
});
