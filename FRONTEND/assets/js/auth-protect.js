// Route protection
document.addEventListener("DOMContentLoaded", () => {
    try {
        const token = localStorage.getItem("token");
        const user = JSON.parse(localStorage.getItem("user"));
        if (!token || !user || !user.email) {
            throw new Error("Unauthenticated");
        }
    } catch (error) {
        console.error("Authentication check failed:", error);
        window.location.href = "login.html";
    }
});