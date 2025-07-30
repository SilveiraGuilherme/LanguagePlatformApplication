document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "login.html";
    return;
  }

  const flashcardContainer = document.getElementById("flashcardContainer");
  const ratingSection = document.getElementById("ratingSection");
  const nextButtonSection = document.getElementById("nextButtonSection");

  let flashcards = [];
  let currentFlashcardIndex = 0;
  let userAnswers = [];

  function fetchFlashcards() {
    const sessionId = localStorage.getItem("sessionId");
    if (!sessionId) {
      flashcardContainer.innerHTML = "<p>No session found. Please start a session first.</p>";
      return;
    }

    fetch(`http://localhost:8080/api/practice-session-flashcards/session/${sessionId}/prioritized?limit=10`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) throw new Error("Failed to fetch flashcards.");
        return response.json();
      })
      .then((data) => {
        flashcards = data;
        renderFlashcard();
      })
      .catch((error) => {
        flashcardContainer.innerHTML = `<p>Error loading flashcards: ${error.message}</p>`;
      });
  }

  function renderFlashcard() {
    const flashcard = flashcards[currentFlashcardIndex];
    if (!flashcard) {
      flashcardContainer.innerHTML = "<p>No flashcards available.</p>";
      ratingSection.style.display = "none";
      nextButtonSection.style.display = "none";
      return;
    }

    ratingSection.style.display = "none";
    nextButtonSection.style.display = "none";

    flashcardContainer.innerHTML = `
      <div class="flashcard">
        <h2>${flashcard.question}</h2>
        <ul>
          ${flashcard.options
        .map(
          (option, idx) =>
            `<li><button class="option-btn" data-index="${idx}">${option}</button></li>`
        )
        .join("")}
        </ul>
      </div>
    `;

    document.querySelectorAll(".option-btn").forEach((btn) =>
      btn.addEventListener("click", handleOptionClick)
    );
  }

  function handleOptionClick(event) {
    const selectedIndex = event.target.getAttribute("data-index");
    const selectedOption = flashcards[currentFlashcardIndex].options[selectedIndex];

    userAnswers[currentFlashcardIndex] = {
      flashcardId: flashcards[currentFlashcardIndex].id,
      selectedAnswer: selectedOption,
      rating: null,
    };

    ratingSection.style.display = "block";
  }

  document.querySelectorAll(".rating-btn").forEach((btn) =>
    btn.addEventListener("click", (event) => {
      const rating = event.target.getAttribute("data-rating");
      userAnswers[currentFlashcardIndex].rating = rating;
      nextButtonSection.style.display = "block";
    })
  );

  document.querySelector("#next-button-section button")?.addEventListener("click", () => {
    currentFlashcardIndex++;
    if (currentFlashcardIndex < flashcards.length) {
      renderFlashcard();
    } else {
      submitResults();
    }
  });

  function submitResults() {
    fetch("http://localhost:8080/api/quiz-results", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userAnswers),
    })
      .then((response) => {
        if (!response.ok) throw new Error("Failed to submit results.");
        window.location.href = "quiz-history.html";
      })
      .catch((error) => {
        flashcardContainer.innerHTML = `<p>Error submitting results: ${error.message}</p>`;
      });
  }

  fetchFlashcards();
});
