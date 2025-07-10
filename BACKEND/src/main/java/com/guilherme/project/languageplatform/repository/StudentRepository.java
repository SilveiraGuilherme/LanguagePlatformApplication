package com.guilherme.project.languageplatform.repository;

import com.guilherme.project.languageplatform.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}