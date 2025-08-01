import { fetchQuizResultById } from './api-config.js';

document.addEventListener('DOMContentLoaded', async () => {
  const resultID = localStorage.getItem('latestResultID');
  const resultContainer = document.getElementById('resultSummary');
  if (!resultContainer) return;

  if (!resultID) {
    resultContainer.innerHTML = '<p class="text-warning">No recent quiz result found.</p>';
    return;
  }

  try {
    const result = await fetchQuizResultById(resultID);

    const {
      difficultyLevel,
      totalQuestions,
      correctAnswers,
      scorePercentage,
      completionTime,
      studentName
    } = result;

    document.getElementById('studentName').textContent = studentName || '';
    document.getElementById('difficultyLevel').textContent = difficultyLevel || '';
    document.getElementById('totalQuestions').textContent = totalQuestions != null ? totalQuestions : '';
    document.getElementById('correctAnswers').textContent = correctAnswers != null ? correctAnswers : '';
    document.getElementById('scorePercentage').textContent = scorePercentage != null ? scorePercentage : '';
    document.getElementById('completionTime').textContent = completionTime ? new Date(completionTime).toLocaleString() : '';
  } catch (error) {
    resultContainer.innerHTML = `<p class="text-danger">Error loading result: ${error.message}</p>`;
    console.error('Error fetching quiz result:', error);
  }
});