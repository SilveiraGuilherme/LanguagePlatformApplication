

// Fetch the latest quiz result by resultID stored in localStorage
document.addEventListener('DOMContentLoaded', function () {
  const resultID = localStorage.getItem('latestResultID');
  const resultContainer = document.getElementById('quizHistoryList');

  if (!resultID) {
    resultContainer.innerHTML = '<p>No recent quiz result found.</p>';
    return;
  }

  fetch(`/api/quiz-results/${resultID}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Failed to fetch quiz result');
      }
      return response.json();
    })
    .then(result => {
      const {
        resultID,
        userID,
        difficultyLevel,
        totalQuestions,
        correctAnswers,
        scorePercentage,
        completionTime,
        studentName
      } = result;

      resultContainer.innerHTML = `
        <div class="quiz-result-card">
          <h3>Result ID: ${resultID}</h3>
          <p>Student: ${studentName}</p>
          <p>Difficulty: ${difficultyLevel}</p>
          <p>Score: ${scorePercentage}% (${correctAnswers}/${totalQuestions})</p>
          <p>Completed at: ${new Date(completionTime).toLocaleString()}</p>
        </div>
      `;

      const viewAllButton = document.createElement('button');
      viewAllButton.textContent = 'View All Quiz Results';
      viewAllButton.className = 'btn btn-primary';
      viewAllButton.style.marginTop = '20px';
      viewAllButton.onclick = () => {
        window.location.href = 'quiz-history.html';
      };
      resultContainer.appendChild(viewAllButton);
    })
    .catch(error => {
      resultContainer.innerHTML = `<p>Error loading result: ${error.message}</p>`;
    });
});