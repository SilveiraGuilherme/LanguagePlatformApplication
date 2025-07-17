package com.project.languageplatform.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuizResultResponseDTO {
    // Variables
    private Long resultID;
    private Long userID;
    private Long sessionID;
    private String difficultyLevel;
    private int totalQuestions;
    private int correctAnswers;
    private BigDecimal scorePercentage;
    private LocalDateTime completionTime;
    private String studentName;

    // Constructor
    public QuizResultResponseDTO() {
    }

    public QuizResultResponseDTO(Long resultID, Long userID, Long sessionID, String difficultyLevel,
            int totalQuestions, int correctAnswers, BigDecimal scorePercentage,
            LocalDateTime completionTime, String studentName) {
        this.resultID = resultID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.difficultyLevel = difficultyLevel;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.scorePercentage = scorePercentage;
        this.completionTime = completionTime;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getResultID() {
        return resultID;
    }

    public void setResultID(Long resultID) {
        this.resultID = resultID;
    }

    public Long getStudentID() {
        return userID;
    }

    public void setStudentID(Long userID) {
        this.userID = userID;
    }

    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
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

    public BigDecimal getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(BigDecimal scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "QuizResultResponseDTO{" +
                "resultID=" + resultID +
                ", userID=" + userID +
                ", sessionID=" + sessionID +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", correctAnswers=" + correctAnswers +
                ", scorePercentage=" + scorePercentage +
                ", completionTime=" + completionTime +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}