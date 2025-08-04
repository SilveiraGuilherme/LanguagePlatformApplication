//const BASE_URL = 'http://localhost:8080';
const BASE_URL = 'https://language-learning-backend-h4engnf2e0ezc8ch.francecentral-01.azurewebsites.net';

function getAuthHeaders() {
    const token = localStorage.getItem('token');
    return token ? { Authorization: `Bearer ${token}` } : {};
}

async function handleResponse(response) {
    let data = {};
    const contentType = response.headers.get('content-type') || '';
    if (contentType.includes('application/json')) {
        try { data = await response.json(); } catch { } // ignore parse errors
    }

    if (!response.ok) {
        const errorMsg = data.message || data.error || response.statusText || 'API request failed';
        throw new Error(errorMsg);
    }

    return data;
}

// Generic method helpers
async function get(endpoint, query = null) {
    let url = `${BASE_URL}${endpoint}`;
    if (query && typeof query === 'object') {
        const qs = Object.entries(query)
            .map(([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(v)}`)
            .join('&');
        if (qs) url += `?${qs}`;
    }
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            ...getAuthHeaders()
        }
    });
    return handleResponse(response);
}

async function post(endpoint, data) {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...getAuthHeaders()
        },
        body: JSON.stringify(data)
    });
    return handleResponse(response);
}

async function put(endpoint, data) {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...getAuthHeaders()
        },
        body: JSON.stringify(data)
    });
    return handleResponse(response);
}

async function del(endpoint) {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getAuthHeaders()
        }
    });
    return handleResponse(response);
}

/* ------------------ Domain APIs ------------------ */

// Users / Students
export function getStudentById(id) { return get(`/api/students/${id}`); }
// export function updateStudent(id, payload) { return put(`/api/students/${id}`, payload); }
// export function deleteStudent(id) { return del(`/api/students/${id}`); }
// export function getAllStudents() { return get(`/api/students`); }
// export function createStudent(payload) { return post(`/api/students`, payload); }

// Quiz Results
export function fetchQuizResultById(resultID) { return get(`/api/quiz-results/${resultID}`); }
export function submitQuiz(payload) { return post(`/api/quiz-results/submit`, payload); }
export function getQuizResultsByUserID(userID) { return get(`/api/quiz-results/user/${userID}`); }
// export function updateQuizResult(resultID, payload) { return put(`/api/quiz-results/${resultID}`, payload); }
// export function deleteQuizResult(resultID) { return del(`/api/quiz-results/${resultID}`); }
// export function getAllQuizResults() { return get(`/api/quiz-results`); }
export function createMultipleQuizResults(payloadArray) { return post(`/api/quiz-results`, payloadArray); }

// Practice Sessions
export function startPracticeSession(userID) { return post(`/api/practice-sessions/start`, { userID: parseInt(userID, 10) }); }
export function getOngoingSession(userID) { return get(`/api/practice-sessions/ongoing/${userID}`); }
// export function getSessionById(id) { return get(`/api/practice-sessions/${id}`); }
// export function updateSession(id, payload) { return put(`/api/practice-sessions/${id}`, payload); }
// export function deleteSession(id) { return del(`/api/practice-sessions/${id}`); }
// export function getAllSessions() { return get(`/api/practice-sessions`); }

// Practice Session Flashcards
export function updateRating(sessionId, flashCardId, rating, userId) { return put(`/api/practice-session-flashcards/${sessionId}/${flashCardId}/rating`, {rating, userID: userId}); }
export function getPrioritizedFlashCards(sessionId, limit = 10) { return get(`/api/practice-session-flashcards/session/${sessionId}/prioritized`, { limit }); }
export function getNextSessionFlashCards(userId, limit = 10) {return get(`/api/practice-session-flashcards/next-flashcards/${userId}?limit=${limit}`); }
// export function getAllPracticeSessionFlashcards() { return get(`/api/practice-session-flashcards`); }
// export function createPracticeSessionFlashcard(payload) { return post(`/api/practice-session-flashcards`, payload); }
// export function createSimplifiedPracticeSessionFlashcard(payload) { return post(`/api/practice-session-flashcards/create`, payload); }
// export function getPracticeSessionFlashcardById(sessionId, flashCardId) { return get(`/api/practice-session-flashcards/${sessionId}/${flashCardId}`); }
// export function deletePracticeSessionFlashcard(sessionId, flashCardId) { return del(`/api/practice-session-flashcards/${sessionId}/${flashCardId}`); }
// export function getBySessionId(sessionId) { return get(`/api/practice-session-flashcards/session/${sessionId}`); }

// Flashcards
export function getAllFlashCards() { return get(`/api/flashcards`); }
export function createFlashCard(payload) { return post(`/api/flashcards`, payload); }
// export function getFlashCardById(id) { return get(`/api/flashcards/${id}`); }
// export function updateFlashCard(id, payload) { return put(`/api/flashcards/${id}`, payload); }
// export function deleteFlashCard(id) { return del(`/api/flashcards/${id}`); }
// export function getFlashCardsByDifficulty(difficultyLevel) { return get(`/api/flashcards/filter`, { difficultyLevel }); }

// Authentication
export function register(payload) { return post(`/api/auth/register`, payload); }
export function login(payload) { return post(`/api/auth/login`, payload); }
// export function resetPassword(payload) { return post(`/api/auth/reset-password`, payload); }
// export function requestResetToken(payload) { return post(`/api/auth/request-reset`, payload); }
// export function changePassword(payload) { return post(`/api/auth/change-password`, payload); }