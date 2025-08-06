package com.project.languageplatform.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.languageplatform.enums.DifficultyLevel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

// Represents the result of a quiz taken by a user.
@Entity
@Table(name = "QuizResult")
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultID;

    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "sessionID", nullable = true)
    private PracticeSession session;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "difficultyLevel")
    private DifficultyLevel difficultyLevel;

    @NotNull
    @Column(nullable = false)
    private int totalQuestions;

    @NotNull
    @Column(nullable = false)
    private int correctAnswers;

    // Timestamp marking when the quiz was completed.
    @NotNull
    @Column(name = "completionTime", nullable = false)
    private LocalDateTime completionTime;

    // Calculated percentage score for the quiz.
    @NotNull
    @Column(name = "scorePercentage")
    private double scorePercentage;

    public QuizResult() {
    }

    public QuizResult(User user, PracticeSession session, DifficultyLevel difficultyLevel, int totalQuestions,
            int correctAnswers) {
        this.user = user;
        this.session = session;
        this.difficultyLevel = difficultyLevel;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.completionTime = LocalDateTime.now();
    }


    public Long getResultID() {
        return resultID;
    }

    public void setResultID(Long resultID) {
        this.resultID = resultID;
    }

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

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    @Override
    public String toString() {
        return "QuizResult{" + "resultID=" + resultID + ", user=" + user.getUserID() + ", difficultyLevel="
                + difficultyLevel + ", totalQuestions=" + totalQuestions + ", correctAnswers=" + correctAnswers
                + ", completionTime=" + completionTime + '}';
    }
}
