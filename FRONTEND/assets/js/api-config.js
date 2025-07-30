const API_BASE = "http://localhost:8080";

// Helper to get headers with token
const authHeaders = () => {
    const token = localStorage.getItem("token");
    return {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
    };
};

const api = {
    headers: authHeaders,
    
    auth: {
        login: `${API_BASE}/api/auth/login`,
        register: `${API_BASE}/api/auth/register`,
        requestReset: `${API_BASE}/api/auth/request-reset`,
        resetPassword: `${API_BASE}/api/auth/reset-password`,
        changePassword: `${API_BASE}/api/auth/change-password`,
    },
    users: {
        getAll: `${API_BASE}/api/students`,
        create: `${API_BASE}/api/students`,
        getById: (id) => `${API_BASE}/api/students/${id}`,
        update: (id) => `${API_BASE}/api/students/${id}`,
        delete: (id) => `${API_BASE}/api/students/${id}`,
    },
    flashcards: {
        getAll: `${API_BASE}/api/flashcards`,
        create: `${API_BASE}/api/flashcards`,
        getById: (id) => `${API_BASE}/api/flashcards/${id}`,
        update: (id) => `${API_BASE}/api/flashcards/${id}`,
        delete: (id) => `${API_BASE}/api/flashcards/${id}`,
        filterByDifficulty: (level) => `${API_BASE}/api/flashcards/filter?difficultyLevel=${level}`,
    },
    sessions: {
        getAll: `${API_BASE}/api/practice-sessions`,
        start: `${API_BASE}/api/practice-sessions/start`,
        getById: (id) => `${API_BASE}/api/practice-sessions/${id}`,
        update: (id) => `${API_BASE}/api/practice-sessions/${id}`,
        delete: (id) => `${API_BASE}/api/practice-sessions/${id}`,
        getOngoing: (userId) => `${API_BASE}/api/practice-sessions/ongoing/${userId}`,
    },
    sessionFlashcards: {
        getAll: `${API_BASE}/api/practice-session-flashcards`,
        create: `${API_BASE}/api/practice-session-flashcards`,
        createSimplified: `${API_BASE}/api/practice-session-flashcards/create`,
        getBySession: (sessionId) => `${API_BASE}/api/practice-session-flashcards/session/${sessionId}`,
        getPrioritized: (sessionId, limit = 10) =>
            `${API_BASE}/api/practice-session-flashcards/session/${sessionId}/prioritized?limit=${limit}`,
        getById: (sessionId, flashCardId) =>
            `${API_BASE}/api/practice-session-flashcards/${sessionId}/${flashCardId}`,
        updateRating: (sessionId, flashCardId) =>
            `${API_BASE}/api/practice-session-flashcards/${sessionId}/${flashCardId}/rating`,
        delete: (sessionId, flashCardId) =>
            `${API_BASE}/api/practice-session-flashcards/${sessionId}/${flashCardId}`,
    },
    quizResults: {
        getAll: `${API_BASE}/quiz-results`,
        create: `${API_BASE}/quiz-results`,
        getById: (id) => `${API_BASE}/quiz-results/${id}`,
        update: (id) => `${API_BASE}/quiz-results/${id}`,
        delete: (id) => `${API_BASE}/quiz-results/${id}`,
        submit: `${API_BASE}/quiz-results/submit`,
    },
};

export default api;