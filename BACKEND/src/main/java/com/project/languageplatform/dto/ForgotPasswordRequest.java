package com.project.languageplatform.dto;

public class ForgotPasswordRequest {
    // Variable
    private String email;

    // Constructor
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