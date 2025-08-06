package com.project.languageplatform.controller;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.languageplatform.dto.ChangePasswordRequest;
import com.project.languageplatform.dto.LoginRequest;
import com.project.languageplatform.dto.RegisterRequest;
import com.project.languageplatform.security.AuthenticationService;

@Validated
@RestController
@RequestMapping("/api/auth")
// Controller handling authentication-related actions like login, registration, and password management
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Authenticate user and return JWT token
    @PostMapping("/login")
    public ResponseEntity<java.util.Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Generate a password reset token for the given email
    @PostMapping("/request-reset")
    public ResponseEntity<String> requestResetToken(@RequestBody java.util.Map<String, String> payload) {
        String email = payload.get("email");
        String token = authService.generateResetToken(email);
        return ResponseEntity.ok("Password reset token: " + token); // In production, you would email this
    }

    // Reset password using email, token, and new password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody java.util.Map<String, String> payload) {
        String email = payload.get("email");
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        authService.resetPassword(email, token, newPassword);
        return ResponseEntity.ok("Password reset successfully.");
    }

    // Change password for an authenticated user
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request.getEmail(), request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully.");
    }
}