package com.project.languageplatform.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

import com.project.languageplatform.dto.LoginRequest;
import com.project.languageplatform.dto.RegisterRequest;
import com.project.languageplatform.entity.User;
import com.project.languageplatform.repository.UserRepository;

// Handles authentication logic including login, registration, and password operations
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    // Registers a new user and returns a JWT token
    public String register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    // Authenticates user and returns token and user details
    public Map<String, Object> login(LoginRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userID", user.getUserID());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole().name());

        return response;
    }

    // Generates a JWT token for password reset
    public String generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with given email not found."));
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // This token can be reused for password reset
    }

    // Resets user password after validating token
    public void resetPassword(String email, String token, String newPassword) {
        if (!jwtUtil.validateToken(token, email)) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with given email not found."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    // Changes password after verifying current one
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}