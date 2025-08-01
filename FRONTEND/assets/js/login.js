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
            const { token, userID, firstName, lastName, email, role } = await response.json();

            if (!token || !email) {
                throw new Error('Incomplete login response from server.');
            }

            localStorage.setItem('token', token);
            localStorage.setItem('userID', userID);
            localStorage.setItem('firstName', firstName);
            localStorage.setItem('lastName', lastName);
            localStorage.setItem('email', email);
            localStorage.setItem('role', role);
            window.location.href = 'dashboard.html';
        } else {
            errorMessage.textContent = 'Invalid email or password. Please check your credentials.';
        }
    } catch (error) {
        errorMessage.textContent = error.message || 'Server error. Please try again later.';
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

        try {
            const result = await response.json();

            if (response.ok) {
                message.textContent = "Registration successful! You can now log in.";
                message.style.color = "green";
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 1500);
            } else {
                message.textContent = result.message || "Registration failed.";
                message.style.color = "red";
            }
        } catch (err) {
            if (response.ok) {
                message.textContent = "Registration successful! You can now log in.";
                message.style.color = "green";
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 1500);
            } else {
                message.textContent = "Something went wrong. Please try again later.";
                message.style.color = "red";
            }
        }
    } catch (error) {
        message.textContent = "Something went wrong. Please try again later.";
        message.style.color = "red";
    }
}

document.getElementById('loginForm')?.addEventListener('submit', handleLogin);
document.getElementById("registerForm")?.addEventListener("submit", handleRegister);