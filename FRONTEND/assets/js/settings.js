// Display user info
document.getElementById("userFullName").textContent = user.firstName + " " + user.lastName;
document.getElementById("userEmail").textContent = user.email;

// Hide delete button for admins
if (user.role === "ADMIN") {
    document.getElementById("deleteAccountBtn").style.display = "none";
}

// Handle account deletion
document.getElementById("deleteAccountBtn").addEventListener("click", function () {
    if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
        fetch("http://localhost:8080/api/user/delete", {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).then(response => {
            if (response.ok) {
                alert("Account deleted successfully.");
                localStorage.clear();
                window.location.href = "login.html";
            } else {
                alert("Failed to delete account.");
            }
        }).catch(() => {
            alert("Error occurred. Try again later.");
        });
    }
});

// Handle profile update
document.getElementById("editProfileForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const updatedUser = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: user.email
    };
    fetch("http://localhost:8080/api/user/update", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(updatedUser)
    })
        .then(res => {
            if (res.ok) {
                alert("Profile updated successfully.");
                localStorage.setItem("user", JSON.stringify(updatedUser));
                window.location.reload();
            } else {
                alert("Failed to update profile.");
            }
        }).catch(() => {
            alert("Network error. Please try again.");
        });
});

// Handle password change
document.getElementById("changePasswordForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const data = {
        email: user.email,
        currentPassword: document.getElementById("currentPassword").value,
        newPassword: document.getElementById("newPassword").value
    };
    fetch("http://localhost:8080/api/auth/change-password", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (res.ok) {
                alert("Password changed successfully.");
                document.getElementById("changePasswordForm").reset();
            } else {
                alert("Failed to change password.");
            }
        }).catch(() => {
            alert("Network error. Please try again.");
        });
});

// Pre-fill form with current user info
document.getElementById("firstName").value = user.firstName;
document.getElementById("lastName").value = user.lastName;