package com.guilherme.project.languageplatform.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "PracticeSession")
public class PracticeSession {
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "studentID", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus sessionStatus = SessionStatus.ONGOING;

    // Enum for status
    public enum SessionStatus {
        ONGOING,
        COMPLETED
    }

    // Constructors
    public PracticeSession() {
    }

    public PracticeSession(Student student) {
        this.student = student;
        this.startTime = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.startTime == null) {
            this.startTime = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    @Override
    public String toString() {
        return "PracticeSession{" + "sessionID=" + sessionID + ", student=" + student + ", startTime=" + startTime
                + ", endTime=" + endTime + ", sessionStatus=" + sessionStatus + '}';
    }
}
