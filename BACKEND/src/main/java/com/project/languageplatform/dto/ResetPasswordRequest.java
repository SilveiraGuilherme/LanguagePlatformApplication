package com.project.languageplatform.dto;

// This class is used to encapsulate the data needed to reset a user's password.
public class ResetPasswordRequest {
    // Variables
    private String email;
    private String token;
    private String newPassword;

    // Constructor
    public ResetPasswordRequest() {
    }
    public ResetPasswordRequest(String email, String token, String newPassword) {
        this.email = email;
        this.token = token;
        this.newPassword = newPassword;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}