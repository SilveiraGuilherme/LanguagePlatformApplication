// login-protect.js
// Protects routes by verifying JWT token presence and validity in localStorage.
// Redirects to login page if the token is missing, malformed, or expired.

import { getStudentById } from './api-config.js';

// Decode JWT payload from token
function parseJwtPayload(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const binary = atob(base64);
    const bytes = Uint8Array.from([...binary].map(c => c.charCodeAt(0)));
    return JSON.parse(new TextDecoder().decode(bytes));
}

// Check token validity when the page loads
document.addEventListener("DOMContentLoaded", async () => {
    try {
        const token = localStorage.getItem("token");
        const userRaw = localStorage.getItem("user");
        if (!token || !userRaw) {
            throw new Error("Missing token or user data");
        }

        const user = JSON.parse(userRaw);
        if (!user || !user.email || !user.userID) {
            throw new Error("Malformed user object");
        }

        // Decode JWT payload safely using helper
        const parts = token.split('.');
        if (parts.length !== 3) {
            throw new Error("Invalid token format");
        }
        const payload = parseJwtPayload(token);

        const currentTime = Math.floor(Date.now() / 1000);
        if (payload.exp && payload.exp < currentTime) {
            throw new Error("Token expired");
        }
        // Optionally validate user ID with backend
        await getStudentById(user.userID);

    } catch (error) {
        console.error("Authentication check failed:", error);
        window.location.href = "login.html";
    }
});