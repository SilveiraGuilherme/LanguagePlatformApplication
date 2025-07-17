package com.project.languageplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.languageplatform.entity.User;
import com.project.languageplatform.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
// Service layer for managing Student entities
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Retrieve all students from the database
    public List<User> getAllStudents() {
        return userRepository.findAll();
    }

    // Find a student by their ID
    public Optional<User> getStudentById(Long id) {
        return userRepository.findById(id);
    }

    // Save or update a student in the database
    public User saveStudent(User user) {
        return userRepository.save(user);
    }

    // Delete a student by their ID
    public void deleteStudent(Long id) {
        userRepository.deleteById(id);
    }
}