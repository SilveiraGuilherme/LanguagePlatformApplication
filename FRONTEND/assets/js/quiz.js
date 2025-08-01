import {
  startPracticeSession,
  getPrioritizedFlashCards,
  getAllFlashCards,
  submitQuiz,
  createMultipleQuizResults
} from './api-config.js';

document.addEventListener('DOMContentLoaded', () => {
  const token = localStorage.getItem('token');
  if (!token) {
    window.location.href = 'login.html';
    return;
  }

  const startSessionSection = document.getElementById('startSessionSection');
  const startSessionBtn = document.getElementById('startSessionBtn');
  const flashcardContainer = document.getElementById('flashcardContainer');
  const ratingSection = document.getElementById('ratingSection');
  const nextButtonSection = document.getElementById('nextButtonSection');
  const confirmSection = document.getElementById('confirmSection');

  let flashcards = [];
  let currentFlashcardIndex = 0;
  let userAnswers = [];
  let selectedOptionIndex = null;

  async function startQuizSession() {
    const userId = localStorage.getItem('userID');
    if (!userId) {
      flashcardContainer.innerHTML = '<p>User ID not found. Please log in again.</p>';
      return;
    }

    try {
      const session = await startPracticeSession(userId);
      localStorage.setItem('sessionId', session.sessionID);
      if (startSessionSection) startSessionSection.style.display = 'none';
      await fetchFlashcards();
    } catch (error) {
      if (flashcardContainer) {
        flashcardContainer.innerHTML = `<p>Error starting session: ${error.message}</p>`;
      }
    }
  }

  if (startSessionBtn) startSessionBtn.addEventListener('click', startQuizSession);

  async function fetchFlashcards() {
    const sessionId = localStorage.getItem('sessionId');
    if (!sessionId) {
      if (flashcardContainer) flashcardContainer.innerHTML = '<p>No session found. Please start a session first.</p>';
      return;
    }

    try {
      const data = await getPrioritizedFlashCards(sessionId, 10);;
      flashcards = Array.isArray(data) ? data : [];
      renderFlashcard();
    } catch (error) {
      if (flashcardContainer) flashcardContainer.innerHTML = `<p>Error loading flashcards: ${error.message}</p>`;
    }
  }

  function updateNextButtonText() {
    const nextBtn = nextButtonSection?.querySelector('button');
    if (!nextBtn) return;
    if (currentFlashcardIndex === flashcards.length - 1) {
      nextBtn.innerText = 'Submit';
    } else {
      nextBtn.innerText = 'Next';
    }
  }

  function attachOptionHandlers() {
    document.querySelectorAll('.option-btn').forEach((btn) => {
      btn.addEventListener('click', handleOptionClick);
    });
  }

  function attachRatingHandlers() {
    document.querySelectorAll('.rating-btn').forEach((btn) => {
      btn.addEventListener('click', (event) => {
        const rating = event.target.getAttribute('data-rating');
        if (!userAnswers[currentFlashcardIndex]) return; // guard
        userAnswers[currentFlashcardIndex].rating = rating;
        document.querySelectorAll('.rating-btn').forEach((b) => b.classList.remove('selected-rating'));
        event.target.classList.add('selected-rating');

        if (nextButtonSection) {
          nextButtonSection.style.display = 'block';
          updateNextButtonText();
        }
      });
    });
  }

  function handleOptionClick(event) {
    selectedOptionIndex = parseInt(event.target.getAttribute('data-index'));
    const flashcard = flashcards[currentFlashcardIndex];
    if (!flashcard) return;
    const options = [flashcard.optionA, flashcard.optionB, flashcard.optionC, flashcard.optionD];

    // Highlight selection
    document.querySelectorAll('.option-btn').forEach((btn, idx) => {
      btn.classList.remove('selected-option');
      if (idx === selectedOptionIndex) {
        btn.classList.add('selected-option');
      }
    });

    // Save temporary answer
    userAnswers[currentFlashcardIndex] = {
      flashcardId: flashcard.flashCardID,
      selectedAnswer: options[selectedOptionIndex],
      isCorrect: null,
      rating: null
    };

    if (confirmSection) confirmSection.style.display = 'block';
  }

  async function submitResults() {
    const userId = parseInt(localStorage.getItem('userID'));
    const sessionId = parseInt(localStorage.getItem('sessionId'));
    if (!userId || !sessionId) {
      alert('Missing user or session context');
      return;
    }

    const difficultyLevel = 'BEGINNER'; // TODO: make dynamic if needed
    const completionTime = new Date().toISOString();
    const totalQuestions = userAnswers.length;
    const correctAnswers = userAnswers.filter((ans) => ans.isCorrect).length;
    const scorePercentage = totalQuestions ? Math.round((correctAnswers / totalQuestions) * 100) : 0;

    const resultsToSend = userAnswers.map((ans) => ({
      user: { userID: userId },
      session: { sessionID: sessionId },
      difficultyLevel,
      totalQuestions,
      correctAnswers,
      scorePercentage,
      completionTime,
      flashcardId: ans.flashcardId,
      selectedAnswer: ans.selectedAnswer,
      isCorrect: ans.isCorrect,
      rating: ans.rating
    }));

    try {
      const savedResults = await createMultipleQuizResults(resultsToSend);
      if (Array.isArray(savedResults) && savedResults.length > 0) {
        const firstResultId = savedResults[0].resultID;
        localStorage.setItem('latestResultID', firstResultId);
        window.location.href = 'results.html';
      } else {
        throw new Error('No result ID returned from server.');
      }
    } catch (error) {
      const errorContainer = document.getElementById('flashcardContainer');
      if (errorContainer) {
        errorContainer.innerHTML = `<p>Error submitting results: ${error.message}</p>`;
      } else {
        alert('Error submitting results: ' + error.message);
      }
    }
  }

  function renderFlashcard() {
    const flashcard = flashcards[currentFlashcardIndex];
    if (!flashcard) {
      document.getElementById('flashcard-content').innerHTML = '<p>No flashcards available.</p>';
      if (ratingSection) ratingSection.style.display = 'none';
      if (nextButtonSection) nextButtonSection.style.display = 'none';
      return;
    }

    if (ratingSection) ratingSection.style.display = 'none';
    if (nextButtonSection) nextButtonSection.style.display = 'none';
    if (confirmSection) confirmSection.style.display = 'none';

    document.getElementById('flashcard-content').innerHTML = `
      <div class="flashcard">
        <h2>${flashcard.sentence}</h2>
        <ul>
          ${[flashcard.optionA, flashcard.optionB, flashcard.optionC, flashcard.optionD]
        .map(
          (option, idx) =>
            `<li><button class="option-btn" data-index="${idx}">${option}</button></li>`
        )
        .join('')}
        </ul>
      </div>
    `;

    attachOptionHandlers();

    // Reset rating selection UI
    document.querySelectorAll('.rating-btn').forEach((b) => b.classList.remove('selected-rating'));

    updateNextButtonText();

    const nextBtn = nextButtonSection?.querySelector('button');
    if (nextBtn) {
      nextBtn.onclick = () => {
        if (nextBtn.innerText.trim().toLowerCase() === 'submit') {
          // Require rating before final submit
          if (!userAnswers[currentFlashcardIndex]?.rating) {
            alert('Please rate this flashcard before submitting.');
            return;
          }
          submitResults();
        } else {
          // Next: ensure rating exists for current before moving
          if (!userAnswers[currentFlashcardIndex]?.rating) {
            alert('Please rate this flashcard before continuing.');
            return;
          }
          currentFlashcardIndex++;
          renderFlashcard();
        }
      };
    }

    if (nextButtonSection) nextButtonSection.style.display = 'none';
  }

  // Confirm button logic
  document.getElementById('confirm-button')?.addEventListener('click', () => {
    const flashcard = flashcards[currentFlashcardIndex];
    if (!flashcard || !userAnswers[currentFlashcardIndex]) return;

    const correctAnswer = flashcard.correctAnswer;
    const selectedAnswer = userAnswers[currentFlashcardIndex].selectedAnswer;
    userAnswers[currentFlashcardIndex].isCorrect = selectedAnswer === correctAnswer;

    // Disable option buttons and style
    document.querySelectorAll('.option-btn').forEach((btn) => {
      btn.disabled = true;
      if (btn.innerText === correctAnswer) {
        btn.classList.add('correct');
      } else if (btn.innerText === selectedAnswer) {
        btn.classList.add('incorrect');
      }
    });

    if (ratingSection) {
      ratingSection.style.display = 'block';
      attachRatingHandlers(); // ensure rating buttons are wired
    }
    if (confirmSection) confirmSection.style.display = 'none';
  });
});
