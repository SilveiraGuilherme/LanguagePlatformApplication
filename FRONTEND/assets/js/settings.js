import { updateStudent, deleteStudent, changePassword } from './api-config.js';

// Load user from storage
const userRaw = localStorage.getItem('user');
if (!userRaw) {
    window.location.href = 'login.html';
}
let user;
try {
    user = JSON.parse(userRaw);
} catch {
    window.location.href = 'login.html';
}

// Display user info
document.getElementById('userFullName').textContent = `${user.firstName} ${user.lastName}`;
document.getElementById('userEmail').textContent = user.email;

// Hide delete button for admins
if (user.role === 'ADMIN') {
    document.getElementById('deleteAccountBtn').style.display = 'none';
}

// Handle account deletion
document.getElementById('deleteAccountBtn').addEventListener('click', async function () {
    if (!confirm('Are you sure you want to delete your account? This action cannot be undone.')) return;
    try {
        await deleteStudent(user.userID);
        alert('Account deleted successfully.');
        localStorage.clear();
        window.location.href = 'login.html';
    } catch (err) {
        console.error('Delete account error:', err);
        alert('Failed to delete account. ' + (err.message || ''));
    }
});

// Handle profile update
document.getElementById('editProfileForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const updatedUser = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        email: user.email,
        password: user.password // keep existing if needed; backend might ignore or require
    };
    try {
        const saved = await updateStudent(user.userID, updatedUser);
        alert('Profile updated successfully.');
        // update stored user
        const newUser = { ...user, firstName: updatedUser.firstName, lastName: updatedUser.lastName };
        localStorage.setItem('user', JSON.stringify(newUser));
        window.location.reload();
    } catch (err) {
        console.error('Profile update error:', err);
        alert('Failed to update profile. ' + (err.message || ''));
    }
});

// Handle password change
document.getElementById('changePasswordForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const data = {
        email: user.email,
        currentPassword: document.getElementById('currentPassword').value,
        newPassword: document.getElementById('newPassword').value
    };
    try {
        await changePassword(data);
        alert('Password changed successfully.');
        document.getElementById('changePasswordForm').reset();
    } catch (err) {
        console.error('Change password error:', err);
        alert('Failed to change password. ' + (err.message || ''));
    }
});

// Pre-fill form with current user info
document.getElementById('firstName').value = user.firstName;
document.getElementById('lastName').value = user.lastName;