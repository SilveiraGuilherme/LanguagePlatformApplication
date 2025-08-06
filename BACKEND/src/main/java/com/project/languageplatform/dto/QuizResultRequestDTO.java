package com.project.languageplatform.dto;

import com.project.languageplatform.entity.PracticeSession;
import com.project.languageplatform.entity.User;
import com.project.languageplatform.enums.DifficultyLevel;

import java.time.LocalDateTime;

/**
 * DTO to receive quiz result data from the client.
 */
public class QuizResultRequestDTO {
    private User user;
    private PracticeSession session;
    private DifficultyLevel difficultyLevel;
    private int totalQuestions;
    private int correctAnswers;
    private double scorePercentage;
    private LocalDateTime completionTime;

    // Default constructor
    public QuizResultRequestDTO() {
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PracticeSession getSession() {
        return session;
    }

    public void setSession(PracticeSession session) {
        this.session = session;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }
}