package com.guilherme.project.languageplatform.entity;

import com.guilherme.project.languageplatform.enums.DifficultyLevel;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "FlashCard")
public class FlashCard {
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flashCardID;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sentence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @Column(nullable = false)
    private String optionA;

    @Column(nullable = false)
    private String optionB;

    @Column(nullable = false)
    private String optionC;

    @Column(nullable = false)
    private String optionD;

    @Column(nullable = false)
    private String correctAnswer; // should match one of the options

    @OneToMany(mappedBy = "flashCard", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PracticeSessionFlashCard> practiceSessionFlashCards;

    // Constructors
    public FlashCard() {
    }

    public FlashCard(String sentence, DifficultyLevel difficultyLevel,
            String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.sentence = sentence;
        this.difficultyLevel = difficultyLevel;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    // Getters and setters
    public Long getFlashCardID() {
        return flashCardID;
    }

    public void setFlashCardID(Long flashCardID) {
        this.flashCardID = flashCardID;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "FlashCard{" + "sentence:'" + sentence + '\'' + ", difficultyLevel:'" + difficultyLevel + '\'' + '}';
    }
}