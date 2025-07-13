package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.Student;
import com.guilherme.project.languageplatform.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
// Service layer for managing Student entities
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Retrieve all students from the database
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Find a student by their ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Save or update a student in the database
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Delete a student by their ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}