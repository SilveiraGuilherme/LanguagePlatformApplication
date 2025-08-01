package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.QuizResult;
import com.project.languageplatform.entity.User;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUser(User user);
}