package com.project.languageplatform.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.languageplatform.dto.LoginRequest;
import com.project.languageplatform.dto.RegisterRequest;
import com.project.languageplatform.entity.User;
import com.project.languageplatform.repository.UserRepository;

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

    public String register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    public String login(LoginRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    public String generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with given email not found."));
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // This token can be reused for password reset
    }

    public void resetPassword(String email, String token, String newPassword) {
        if (!jwtUtil.validateToken(token, email)) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with given email not found."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}