import { login as apiLogin, register as apiRegister } from './api-config.js';

async function handleLogin(event) {
    event.preventDefault();

    const email = document.getElementById('email')?.value.trim() || '';
    const password = document.getElementById('password')?.value.trim() || '';
    const errorMessage = document.getElementById('error-message');

    if (!email || !password) {
        if (errorMessage) errorMessage.textContent = 'Please enter both email and password.';
        return;
    }

    try {
        const data = await apiLogin({ email, password });
        const { token, userID, firstName, lastName, email: userEmail, role } = data;

        if (!token || !userEmail) {
            throw new Error('Incomplete login response from server.');
        }

        // Normalize storage
        localStorage.setItem('token', token);
        const userObj = {
            userID,
            firstName,
            lastName,
            email: userEmail,
            role
        };
        localStorage.setItem('user', JSON.stringify(userObj));
        localStorage.setItem('userID', userID);
        localStorage.setItem('firstName', firstName);
        localStorage.setItem('lastName', lastName);
        localStorage.setItem('email', userEmail);
        localStorage.setItem('role', role);

        window.location.href = 'dashboard.html';
    } catch (error) {
        if (errorMessage) errorMessage.textContent = error.message || 'Server error. Please try again later.';
    }
}

async function handleRegister(event) {
    event.preventDefault();

    const firstName = document.getElementById('firstName')?.value.trim() || '';
    const lastName = document.getElementById('lastName')?.value.trim() || '';
    const email = document.getElementById('email')?.value.trim() || '';
    const password = document.getElementById('password')?.value.trim() || '';
    const message = document.getElementById('registerMessage');

    if (!firstName || !lastName || !email || !password) {
        if (message) {
            message.textContent = 'Please fill out all fields.';
            message.style.color = 'red';
        }
        return;
    }

    const payload = { firstName, lastName, email, password };

    try {
        await apiRegister(payload);
        if (message) {
            message.textContent = 'Registration successful! You can now log in.';
            message.style.color = 'green';
        }
        setTimeout(() => { window.location.href = 'login.html'; }, 1500);
    } catch (error) {
        if (message) {
            message.textContent = error.message || 'Registration failed.';
            message.style.color = 'red';
        }
    }
}

document.getElementById('loginForm')?.addEventListener('submit', handleLogin);
document.getElementById('registerForm')?.addEventListener('submit', handleRegister);