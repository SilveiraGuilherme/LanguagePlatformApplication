package com.project.languageplatform.dto;

// DTO for handling forgot password requests
public class ForgotPasswordRequest {
    private String email;

    // Constructors
    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}