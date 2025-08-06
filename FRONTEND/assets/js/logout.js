/****
 * Logs the user out by clearing all stored session data
 * and redirecting to the home (index) page.
 */
function logout() {
    localStorage.clear(); // Remove all user-related data from browser storage
    window.location.href = "index.html"; // Redirect to the homepage
}