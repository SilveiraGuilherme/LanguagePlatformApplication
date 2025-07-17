// Helper class required for a table with a composite primary key
// The unique identifier for this table is made of two fields together.

package com.project.languageplatform.entity.id;

import java.io.Serializable; // Required by JPA to allow object comparison and caching
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class PracticeSessionFlashCardId implements Serializable {
    // Variables
    private Long sessionID;
    private Long flashCardID;

    // Constructors
    public PracticeSessionFlashCardId() {
    }

    public PracticeSessionFlashCardId(Long sessionID, Long flashCardID) {
        this.sessionID = sessionID;
        this.flashCardID = flashCardID;
    }

    // Getters and Setters
    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public Long getFlashCardID() {
        return flashCardID;
    }

    public void setFlashCardID(Long flashCardID) {
        this.flashCardID = flashCardID;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PracticeSessionFlashCardId that))
            return false;
        return Objects.equals(sessionID, that.sessionID) && Objects.equals(flashCardID, that.flashCardID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionID, flashCardID);
    }
}
