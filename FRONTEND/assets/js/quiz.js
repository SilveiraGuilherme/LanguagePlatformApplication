// Imports API functions for session, flashcard fetching, rating, and quiz submission
import {
  startPracticeSession,
  getNextSessionFlashCards,
  updateRating,
  submitQuiz
} from './api-config.js';

// Retrieve the logged-in user's ID from localStorage
function getUserID() {
  return localStorage.getItem('userID');
}

// Retrieve the current practice session ID from localStorage
function getSessionID() {
  return localStorage.getItem('sessionId');
}

// Generate HTML structure for a flashcard question and its options
function renderQuestionContent(flashcard) {
  return `
    <div class="flashcard">
      <h2>${flashcard.sentence}</h2>
      <ul>
        ${[flashcard.optionA, flashcard.optionB, flashcard.optionC, flashcard.optionD]
          .map((option, idx) => `<li><button class="option-btn" data-index="${idx}">${option}</button></li>`)
          .join('')}
      </ul>
    </div>
  `;
}

// Reset rating button styles to default
function resetRatingButtons() {
  document.querySelectorAll('.rating-btn').forEach((b) => b.classList.remove('selected-rating'));
}

// Display error message in the flashcard container
function showError(message) {
  const container = document.getElementById('flashcardContainer') || flashcardContainer;
  if (container) {
    container.innerHTML = `<p>${message}</p>`;
  } else {
    alert(message);
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const token = localStorage.getItem('token');
  if (!token) {
    window.location.href = 'login.html';
    return;
  }

  const startSessionSection = document.getElementById('startSessionSection');
  const startSessionBtn = document.getElementById('startSessionBtn');
  const flashcardContainer = document.getElementById('flashcard-content');
  const ratingSection = document.getElementById('ratingSection');
  const nextButtonSection = document.getElementById('nextButtonSection');
  const confirmSection = document.getElementById('confirmSection');

  let flashcards = [];
  let currentFlashcardIndex = 0;
  let userAnswers = [];
  let selectedOptionIndex = null;

  // Starts a new quiz session and fetches flashcards
  async function startQuizSession() {
    const userId = getUserID();
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
      showError(`Error starting session: ${error.message}`);
    }
  }

  if (startSessionBtn) startSessionBtn.addEventListener('click', startQuizSession);

  // Fetch flashcards for the current session from the backend
  async function fetchFlashcards() {
    const sessionId = getSessionID();
    if (!sessionId) {
      showError('No session found. Please start a session first.');
      return;
    }

    try {
      const userId = getUserID();
      const data = await getNextSessionFlashCards(userId, 10);

      console.log("📦 Flashcards received from backend:", data);

      if (!Array.isArray(data) || data.length === 0) {
        showError('No flashcards available. Try again later.');
        return;
      }

      flashcards = data;
      renderFlashcard();
    } catch (error) {
      showError(`Error loading flashcards: ${error.message}`);
    }
  }

  // Update the button label based on whether it's the last flashcard
  function updateNextButtonText() {
    const nextBtn = nextButtonSection?.querySelector('button');
    if (!nextBtn) return;
    if (currentFlashcardIndex === flashcards.length - 1) {
      nextBtn.innerText = 'Submit';
    } else {
      nextBtn.innerText = 'Next';
    }
  }

  // Add event listeners to answer option buttons
  function attachOptionHandlers() {
    document.querySelectorAll('.option-btn').forEach((btn) => {
      btn.addEventListener('click', handleOptionClick);
    });
  }

  // Add event listeners to rating buttons and update UI based on selection
  function attachRatingHandlers() {
    document.querySelectorAll('.rating-btn').forEach((btn) => {
      const newBtn = btn.cloneNode(true); // Clone to remove existing listeners
      btn.parentNode.replaceChild(newBtn, btn); // Replace the old button

      newBtn.addEventListener('click', (event) => {
        const rating = event.target.getAttribute('data-rating');
        if (!userAnswers[currentFlashcardIndex]) return; // guard
        userAnswers[currentFlashcardIndex].rating = rating;

        document.querySelectorAll('.rating-btn').forEach((b) => b.classList.remove('selected-rating'));
        event.target.classList.add('selected-rating');

        if (nextButtonSection) nextButtonSection.style.display = 'block'; // Show next button section
      });
    });
  }

  // Save selected answer and show confirm button
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

  // Build the quiz result object to be submitted to the backend
  function buildResultObject(userId, sessionId) {
    const difficultyLevel = 'BEGINNER';
    const completionTime = new Date().toISOString();
    const totalQuestions = userAnswers.length;
    const correctAnswers = userAnswers.filter((ans) => ans.isCorrect).length;
    const scorePercentage = totalQuestions ? Math.round((correctAnswers / totalQuestions) * 100) : 0;

    return {
      user: { userID: userId },
      session: { sessionID: sessionId },
      difficultyLevel,
      totalQuestions,
      correctAnswers,
      scorePercentage,
      completionTime
    };
  }

  // Submit the quiz results and redirect to results page
  async function submitResults() {
    const userId = parseInt(getUserID());
    const sessionId = parseInt(getSessionID());
    if (!userId || !sessionId) {
      alert('Missing user or session context');
      return;
    }

    const resultsToSend = buildResultObject(userId, sessionId);

    try {
      const savedResult = await submitQuiz(resultsToSend);
      if (savedResult?.resultID) {
        localStorage.setItem('latestResultID', savedResult.resultID);
        window.location.href = 'results.html';
      } else {
        throw new Error('No result ID returned from server.');
      }
    } catch (error) {
      showError(`Error submitting results: ${error.message}`);
    }
  }

  // Render the current flashcard and set up UI interactions
  function renderFlashcard() {
    const flashcard = flashcards[currentFlashcardIndex];
    if (!flashcard) {
      showError('No flashcards available.');
      if (ratingSection) ratingSection.style.display = 'none';
      if (nextButtonSection) nextButtonSection.style.display = 'none';
      return;
    }

    if (ratingSection) ratingSection.style.display = 'none';
    if (nextButtonSection) nextButtonSection.style.display = 'none';
    if (confirmSection) confirmSection.style.display = 'none';

    // Update question counter
    const questionCounter = document.getElementById('question-counter');
    if (questionCounter) {
      questionCounter.innerText = `${currentFlashcardIndex + 1} / ${flashcards.length}`;
    }

    document.getElementById('flashcard-content').innerHTML = renderQuestionContent(flashcard);

    attachOptionHandlers();

    resetRatingButtons();

    updateNextButtonText();

    const nextBtn = nextButtonSection?.querySelector('button');
    if (nextBtn) {
      nextBtn.onclick = () => {
        const currentAnswer = userAnswers[currentFlashcardIndex];
        if (!currentAnswer?.rating) {
          alert('Please rate this flashcard before continuing.');
          return;
        }

        const sessionId = getSessionID();
        const userId = getUserID();

        updateRating(sessionId, currentAnswer.flashcardId, currentAnswer.rating, userId)
          .then(() => {
            console.log("Rating updated successfully.");
          })
          .catch(err => {
            console.error("Error saving rating to PracticeSessionFlashCard:", err);
          });

        if (nextBtn.innerText.trim().toLowerCase() === 'submit') {
          submitResults();
        } else {
          currentFlashcardIndex++;
          renderFlashcard();
        }
      };
    }

    if (nextButtonSection) nextButtonSection.style.display = 'none';
  }

  // Handle confirm answer logic: check correctness and reveal ratings
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
      attachRatingHandlers();
    }
    if (confirmSection) confirmSection.style.display = 'none';
  });
});
