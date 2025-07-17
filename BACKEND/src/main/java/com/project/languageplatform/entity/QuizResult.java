package com.project.languageplatform.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.languageplatform.enums.DifficultyLevel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "QuizResult")
public class QuizResult {
    // Variables
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

    @NotNull
    @Column(name = "completionTime", nullable = false)
    private LocalDateTime completionTime;

    @NotNull
    @Column(name = "scorePercentage")
    private BigDecimal scorePercentage;

    // Constructors
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


    // Getters and Setters
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

    public BigDecimal getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(BigDecimal scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    @Override
    public String toString() {
        return "QuizResult{" + "resultID=" + resultID + ", user=" + user.getUserID() + ", difficultyLevel="
                + difficultyLevel + ", totalQuestions=" + totalQuestions + ", correctAnswers=" + correctAnswers
                + ", completionTime=" + completionTime + '}';
    }
}
