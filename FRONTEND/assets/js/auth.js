const BASE_URL = 'http://localhost:8080';

async function handleLogin(event) {
    event.preventDefault();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const errorMessage = document.getElementById('error-message');

    if (!email || !password) {
        errorMessage.textContent = 'Please enter both email and password.';
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token);
            window.location.href = 'dashboard.html';
        } else {
            errorMessage.textContent = 'Invalid email or password.';
        }
    } catch (error) {
        errorMessage.textContent = 'Server error. Please try again later.';
    }
}

async function handleRegister(event) {
    event.preventDefault();

    const firstName = document.getElementById("firstName").value.trim();
    const lastName = document.getElementById("lastName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const message = document.getElementById("registerMessage");

    if (!firstName || !lastName || !email || !password) {
        message.textContent = "Please fill out all fields.";
        message.style.color = "red";
        return;
    }

    const payload = { firstName, lastName, email, password };

    try {
        const response = await fetch(`${BASE_URL}/api/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const result = await response.json();

        if (response.ok) {
            message.textContent = "Registration successful! You can now log in.";
            message.style.color = "green";
        } else {
            message.textContent = result.message || "Registration failed.";
            message.style.color = "red";
        }
    } catch (error) {
        message.textContent = "Something went wrong. Please try again later.";
        message.style.color = "red";
    }
}

document.getElementById('loginForm')?.addEventListener('submit', handleLogin);
document.getElementById("registerForm")?.addEventListener("submit", handleRegister);