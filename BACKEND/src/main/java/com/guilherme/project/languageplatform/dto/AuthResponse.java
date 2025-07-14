package com.guilherme.project.languageplatform.dto;

public class AuthResponse {
    // Variable to hold the authentication token
    private String token;

    // Constructor to initialize the AuthResponse with a token
    public AuthResponse() {
    }
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
