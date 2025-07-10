package com.guilherme.project.languageplatform.repository;

import com.guilherme.project.languageplatform.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
}