package com.project.languageplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    // Variables
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    // Constructor
    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String email, String currentPassword, String newPassword) {
        this.email = email;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "email='" + email + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}