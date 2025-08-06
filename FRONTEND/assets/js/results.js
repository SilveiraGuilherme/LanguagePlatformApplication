// Display latest quiz result on page load
import { fetchQuizResultById } from './api-config.js';

document.addEventListener('DOMContentLoaded', async () => {
  // Run on DOM load
  // Fetch result ID
  const resultID = localStorage.getItem('latestResultID');
  const resultContainer = document.getElementById('resultSummary');
  // Stop if no container
  if (!resultContainer) return;

  // No result found
  if (!resultID) {
    resultContainer.innerHTML = '<p class="text-warning">No recent quiz result found.</p>';
    return;
  }

  try {
    // Get and show result
    const result = await fetchQuizResultById(resultID);

    const {
      difficultyLevel,
      totalQuestions,
      correctAnswers,
      scorePercentage,
      completionTime,
      studentName
    } = result;

    // Fill in data
    document.getElementById('studentName').textContent = studentName || '';
    document.getElementById('difficultyLevel').textContent = difficultyLevel || '';
    document.getElementById('totalQuestions').textContent = totalQuestions != null ? totalQuestions : '';
    document.getElementById('correctAnswers').textContent = correctAnswers != null ? correctAnswers : '';
    document.getElementById('scorePercentage').textContent = scorePercentage != null ? scorePercentage : '';
    document.getElementById('completionTime').textContent = completionTime ? new Date(completionTime).toLocaleString() : '';
  } catch (error) {
    // On error
    resultContainer.innerHTML = `<p class="text-danger">Error loading result: ${error.message}</p>`;
    console.error('Error fetching quiz result:', error);
  }
});