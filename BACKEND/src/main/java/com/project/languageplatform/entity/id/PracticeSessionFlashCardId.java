/**
 * Composite key for PracticeSessionFlashCard entity using sessionID and flashCardID.
 */

package com.project.languageplatform.entity.id;

import java.io.Serializable; // Required by JPA to allow object comparison and caching
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class PracticeSessionFlashCardId implements Serializable {
    private Long sessionID;
    private Long flashCardID;

    public PracticeSessionFlashCardId() {
    }

    public PracticeSessionFlashCardId(Long sessionID, Long flashCardID) {
        this.sessionID = sessionID;
        this.flashCardID = flashCardID;
    }

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
