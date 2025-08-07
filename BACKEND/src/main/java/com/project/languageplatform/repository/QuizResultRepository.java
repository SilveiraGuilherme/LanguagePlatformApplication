package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.QuizResult;
import com.project.languageplatform.entity.User;

/**
 * Repository interface for managing QuizResult entities.
 * Provides methods to perform CRUD operations and custom queries related to quiz results.
 */
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    /**
     * Finds all quiz results associated with a given user.
     * 
     * @param user the User entity
     * @return a list of QuizResult entities for the specified user
     */
    List<QuizResult> findByUser(User user);
}