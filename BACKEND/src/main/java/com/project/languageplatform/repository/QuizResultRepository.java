package com.project.languageplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.QuizResult;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
}