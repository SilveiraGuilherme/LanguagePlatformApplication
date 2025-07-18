document.addEventListener("DOMContentLoaded", () => {
    const firstName = localStorage.getItem('firstName');

    const nameSpan = document.getElementById('userNamePlaceholder');
    if (nameSpan) {
        nameSpan.textContent = firstName || 'User';
    }
});