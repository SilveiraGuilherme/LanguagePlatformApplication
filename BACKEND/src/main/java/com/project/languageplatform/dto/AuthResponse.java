package com.project.languageplatform.dto;

public class AuthResponse {
    // Variable to hold the authentication token
    private String token;
    private UserResponseDTO user;

    // Constructor to initialize the AuthResponse with a token
    public AuthResponse() {
    }
    public AuthResponse(String token, UserResponseDTO user) {
        this.token = token;
        this.user = user;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public UserResponseDTO getUser() {
        return user;
    } 
    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
