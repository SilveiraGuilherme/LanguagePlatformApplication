document.addEventListener("DOMContentLoaded", () => {
    const user = JSON.parse(localStorage.getItem('user'));

    const nameSpan = document.getElementById('userNamePlaceholder');
    if (nameSpan) {
        nameSpan.textContent = user?.firstName || 'User';
    }
});