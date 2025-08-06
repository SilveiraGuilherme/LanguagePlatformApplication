package com.project.languageplatform.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PracticeSession")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PracticeSession {
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionID;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull
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

    public PracticeSession(User user) {
        this.user = user;
        this.startTime = LocalDateTime.now();
    }
    
    // Automatically set startTime if not manually set
    @PrePersist
    protected void onCreate() {
        if (this.startTime == null) {
            this.startTime = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "PracticeSession{" + "sessionID=" + sessionID + ", user=" + user + ", startTime=" + startTime
                + ", endTime=" + endTime + ", sessionStatus=" + sessionStatus + '}';
    }
}
