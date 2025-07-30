document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "login.html";
    return;
  }

  const startSessionSection = document.getElementById("startSessionSection");
  const startSessionBtn = document.getElementById("startSessionBtn");
  const flashcardContainer = document.getElementById("flashcardContainer");
  const ratingSection = document.getElementById("ratingSection");
  const nextButtonSection = document.getElementById("nextButtonSection");

  let flashcards = [];
  let currentFlashcardIndex = 0;
  let userAnswers = [];
  let selectedOptionIndex = null;

  function startQuizSession() {
    const userId = localStorage.getItem("userID");
    if (!userId) {
      flashcardContainer.innerHTML = "<p>User ID not found. Please log in again.</p>";
      return;
    }

    fetch("http://localhost:8080/api/practice-sessions/start", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ userID: parseInt(userId) }),
    })
      .then((response) => {
        if (!response.ok) throw new Error("Failed to start session.");
        return response.json();
      })
      .then((session) => {
        localStorage.setItem("sessionId", session.sessionID);
        startSessionSection.style.display = "none";
        fetchFlashcards();
      })
      .catch((error) => {
        flashcardContainer.innerHTML = `<p>Error starting session: ${error.message}</p>`;
      });
  }

  startSessionBtn.addEventListener("click", startQuizSession);


  function fetchFlashcards() {
    const sessionId = localStorage.getItem("sessionId");
    if (!sessionId) {
      flashcardContainer.innerHTML = "<p>No session found. Please start a session first.</p>";
      return;
    }

    fetch("http://localhost:8080/api/flashcards", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.json())
      .then(data => {
        console.log("Flashcards (simple fetch):", data);
        flashcards = data;
        renderFlashcard();
      }).catch((error) => {
        flashcardContainer.innerHTML = `<p>Error loading flashcards: ${error.message}</p>`;
      });;

    // fetch(`http://localhost:8080/api/practice-session-flashcards/session/${sessionId}/prioritized?limit=10`, {
    //   headers: {
    //     Authorization: `Bearer ${token}`,
    //   },
    // })
    //   .then((response) => {
    //     if (!response.ok) throw new Error("Failed to fetch flashcards.");
    //     return response.json();
    //   })
    //   .then((data) => {
    //     flashcards = data;
    //     renderFlashcard();
    //   })
    //   .catch((error) => {
    //     flashcardContainer.innerHTML = `<p>Error loading flashcards: ${error.message}</p>`;
    //   });
  }

  function renderFlashcard() {
    const flashcard = flashcards[currentFlashcardIndex];
    if (!flashcard) {
      document.getElementById("flashcard-content").innerHTML = "<p>No flashcards available.</p>";
      if (ratingSection) ratingSection.style.display = "none";
      if (nextButtonSection) nextButtonSection.style.display = "none";
      return;
    }

    if (ratingSection) ratingSection.style.display = "none";
    if (nextButtonSection) nextButtonSection.style.display = "none";

    document.getElementById("flashcard-content").innerHTML = `
      <div class="flashcard">
        <h2>${flashcard.sentence}</h2>
        <ul>
          ${[flashcard.optionA, flashcard.optionB, flashcard.optionC, flashcard.optionD]
        .map(
          (option, idx) =>
            `<li><button class="option-btn" data-index="${idx}">${option}</button></li>`
        )
        .join("")}
        </ul>
      </div>
    `;
    const confirmSection = document.getElementById("confirmSection");
    if (confirmSection) confirmSection.style.display = "none";

    document.querySelectorAll(".option-btn").forEach((btn) =>
      btn.addEventListener("click", handleOptionClick)
    );
  }

  function handleOptionClick(event) {
    selectedOptionIndex = parseInt(event.target.getAttribute("data-index"));
    const flashcard = flashcards[currentFlashcardIndex];
    const options = [flashcard.optionA, flashcard.optionB, flashcard.optionC, flashcard.optionD];

    // Highlight selected, remove highlight from others
    document.querySelectorAll(".option-btn").forEach((btn, idx) => {
      btn.classList.remove("selected-option");
      if (idx === selectedOptionIndex) {
        btn.classList.add("selected-option");
      }
    });

    // Save answer temporarily (before confirm)
    userAnswers[currentFlashcardIndex] = {
      flashcardId: flashcard.flashCardID,
      selectedAnswer: options[selectedOptionIndex],
      isCorrect: null,
      rating: null,
    };

    const confirmSection = document.getElementById("confirmSection");
    if (confirmSection) confirmSection.style.display = "block";
  }

  document.querySelectorAll(".rating-btn").forEach((btn) =>
    btn.addEventListener("click", (event) => {
      const rating = event.target.getAttribute("data-rating");
      userAnswers[currentFlashcardIndex].rating = rating;

      // Visually highlight selected rating
      document.querySelectorAll(".rating-btn").forEach((b) =>
        b.classList.remove("selected-rating")
      );
      event.target.classList.add("selected-rating");

      console.log("Saved rating:", rating, "for flashcard", userAnswers[currentFlashcardIndex]);

      if (nextButtonSection) nextButtonSection.style.display = "block";
    })
  );

  document.getElementById("confirm-button")?.addEventListener("click", () => {
    const flashcard = flashcards[currentFlashcardIndex];
    const correctAnswer = flashcard.correctAnswer;
    const selectedAnswer = userAnswers[currentFlashcardIndex].selectedAnswer;

    userAnswers[currentFlashcardIndex].isCorrect = selectedAnswer === correctAnswer;

    // Disable option buttons
    document.querySelectorAll(".option-btn").forEach(btn => {
      btn.disabled = true;
      if (btn.innerText === correctAnswer) {
        btn.classList.add("correct");
      } else if (btn.innerText === selectedAnswer) {
        btn.classList.add("incorrect");
      }
    });

    // Show rating section
    if (ratingSection) ratingSection.style.display = "block";
    const confirmSection = document.getElementById("confirmSection");
    if (confirmSection) confirmSection.style.display = "none";
  });

  document.querySelector("#nextButtonSection button")?.addEventListener("click", () => {
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
});
