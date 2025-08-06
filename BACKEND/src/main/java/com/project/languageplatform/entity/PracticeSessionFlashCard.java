package com.project.languageplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.project.languageplatform.enums.Rating;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "PracticeSessionFlashCard")
public class PracticeSessionFlashCard {

    @EmbeddedId
    private PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(); // Composite key: sessionID + flashCardID

    @ManyToOne
    @MapsId("sessionID")
    @JoinColumn(name = "sessionID")
    private PracticeSession session;

    @ManyToOne
    @MapsId("flashCardID")
    @JoinColumn(name = "flashCardID")
    private FlashCard flashCard;

    @Min(0)
    private int positionInQueue; // Used to order flashcards in a session

    @Enumerated(EnumType.STRING)
    private Rating rating = Rating.DONT_KNOW; // Initial rating for new flashcards

    // Default constructor
    public PracticeSessionFlashCard() {
    }

    // Constructor with all fields
    public PracticeSessionFlashCard(PracticeSession session, FlashCard flashCard, int positionInQueue, Rating rating) {
        this.session = session;
        this.flashCard = flashCard;
        this.positionInQueue = positionInQueue;
        this.rating = rating;
        this.id = new PracticeSessionFlashCardId(session.getSessionID(), flashCard.getFlashCardID());
    }

    // Constructor for default rating
    public PracticeSessionFlashCard(PracticeSession session, FlashCard flashCard) {
        this.session = session;
        this.flashCard = flashCard;
    }

    // Getters and Setters
    public PracticeSessionFlashCardId getId() {
        return id;
    }

    public void setId(PracticeSessionFlashCardId id) {
        this.id = id;
    }

    public PracticeSession getSession() {
        return session;
    }

    public void setSession(PracticeSession session) {
        this.session = session;
    }

    public FlashCard getFlashCard() {
        return flashCard;
    }

    public void setFlashCard(FlashCard flashCard) {
        this.flashCard = flashCard;
    }

    public int getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(int positionInQueue) {
        this.positionInQueue = positionInQueue;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
