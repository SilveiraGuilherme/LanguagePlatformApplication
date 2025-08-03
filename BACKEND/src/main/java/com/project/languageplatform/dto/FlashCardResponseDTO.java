package com.project.languageplatform.dto;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;

public class FlashCardResponseDTO {
    private Long flashCardID;
    private String sentence;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private DifficultyLevel difficultyLevel;

    public FlashCardResponseDTO(FlashCard flashCard) {
        this.flashCardID = flashCard.getFlashCardID();
        this.sentence = flashCard.getSentence();
        this.optionA = flashCard.getOptionA();
        this.optionB = flashCard.getOptionB();
        this.optionC = flashCard.getOptionC();
        this.optionD = flashCard.getOptionD();
        this.correctAnswer = flashCard.getCorrectAnswer();
        this.difficultyLevel = flashCard.getDifficultyLevel();
    }

    // Getters
    public Long getFlashCardID() { return flashCardID; }
    public String getSentence() { return sentence; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getCorrectAnswer() { return correctAnswer; }
    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
}