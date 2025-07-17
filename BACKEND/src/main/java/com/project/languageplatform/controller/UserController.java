package com.project.languageplatform.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.languageplatform.entity.User;
import com.project.languageplatform.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all students
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    // Get student by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getStudentById(@PathVariable Long id) {
        return userService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new student
    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<User> createStudent(@RequestBody User student) {
        User createdStudent = userService.saveStudent(student);
        return ResponseEntity.created(URI.create("/api/students/" + createdStudent.getUserID()))
                .body(createdStudent);
    }

    // Update student
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<User> updateStudent(@PathVariable Long id, @RequestBody User student) {
        if (!userService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        student.setUserID(id);
        return ResponseEntity.ok(userService.saveStudent(student));
    }

    // Delete student
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!userService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}