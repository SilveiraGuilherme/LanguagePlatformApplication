package com.guilherme.project.languageplatform.entity;

import java.time.LocalDateTime;

import com.guilherme.project.languageplatform.enums.DifficultyLevel;

import jakarta.persistence.*;

@Entity
@Table(name = "QuizResult")
public class QuizResult {
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultID;

    @ManyToOne
    @JoinColumn(name = "studentID", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "sessionID", nullable = false)
    private PracticeSession session;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficultyLevel")
    private DifficultyLevel difficultyLevel;

    @Column(nullable = false)
    private int totalQuestions;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(name = "completionTime", nullable = false)
    private LocalDateTime timestamp;

    // Constructors
    public QuizResult() {
    }

    public QuizResult(Student student, PracticeSession session, DifficultyLevel difficultyLevel, int totalQuestions,
            int correctAnswers) {
        this.student = student;
        this.session = session;
        this.difficultyLevel = difficultyLevel;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.timestamp = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getResultID() {
        return resultID;
    }

    public void setResultID(Long resultID) {
        this.resultID = resultID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "QuizResult{" + "resultID=" + resultID + ", student=" + student.getStudentID() + ", difficultyLevel="
                + difficultyLevel + ", totalQuestions=" + totalQuestions + ", correctAnswers=" + correctAnswers
                + ", timestamp=" + timestamp + '}';
    }
}
