// Fetch the latest quiz result by resultID stored in localStorage
document.addEventListener('DOMContentLoaded', function () {
  const resultID = localStorage.getItem('latestResultID');
  const resultContainer = document.getElementById('resultSummary');

  if (!resultContainer) {
    console.error('Container element with ID "resultSummary" not found.');
    return;
  }

  if (!resultID) {
    resultContainer.innerHTML = '<p class="text-warning">No recent quiz result found.</p>';
    return;
  }

  const token = localStorage.getItem('token');
  fetch(`http://localhost:8080/api/quiz-results/${resultID}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  })
    .then(response => {
      if (!response.ok) {
        if (response.status === 403) {
          throw new Error('Access forbidden. Please make sure you are logged in with the correct permissions.');
        } else {
          throw new Error(`Failed to fetch quiz result (Status: ${response.status})`);
        }
      }
      return response.json();
    })
    .then(result => {
      const {
        difficultyLevel,
        totalQuestions,
        correctAnswers,
        scorePercentage,
        completionTime,
        studentName
      } = result;

      document.getElementById('studentName').textContent = studentName;
      document.getElementById('difficultyLevel').textContent = difficultyLevel;
      document.getElementById('totalQuestions').textContent = totalQuestions;
      document.getElementById('correctAnswers').textContent = correctAnswers;
      document.getElementById('scorePercentage').textContent = scorePercentage;
      document.getElementById('completionTime').textContent = new Date(completionTime).toLocaleString();
    })
    .catch(error => {
      resultContainer.innerHTML = `<p class="text-danger">Error loading result: ${error.message}</p>`;
    });
});