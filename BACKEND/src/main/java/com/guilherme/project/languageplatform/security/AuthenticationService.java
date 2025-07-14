package com.guilherme.project.languageplatform.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.guilherme.project.languageplatform.dto.LoginRequest;
import com.guilherme.project.languageplatform.dto.RegisterRequest;
import com.guilherme.project.languageplatform.entity.Student;
import com.guilherme.project.languageplatform.repository.StudentRepository;

@Service
public class AuthenticationService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthenticationService(StudentRepository studentRepository, PasswordEncoder passwordEncoder,
                                 JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public String register(RegisterRequest request) {
        Student student = new Student();
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        studentRepository.save(student);
        return jwtUtil.generateToken(student.getEmail());
    }

    public String login(LoginRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        return jwtUtil.generateToken(request.getEmail());
    }
}