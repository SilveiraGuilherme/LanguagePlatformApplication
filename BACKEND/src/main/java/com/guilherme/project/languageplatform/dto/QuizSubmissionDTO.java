package com.guilherme.project.languageplatform.dto;

import java.util.List;

public class QuizSubmissionDTO {
    private Long studentID;
    private List<AnswerDTO> answers;

    // Constructors
    public QuizSubmissionDTO() {
    }

    public QuizSubmissionDTO(Long studentID, List<AnswerDTO> answers) {
        this.studentID = studentID;
        this.answers = answers;
    }

    // Getters and Setters
    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    // Inner class for individual answers
    public static class AnswerDTO {
        private Long flashCardID;
        private String selectedOption;

        public AnswerDTO() {
        }

        public AnswerDTO(Long flashCardID, String selectedOption) {
            this.flashCardID = flashCardID;
            this.selectedOption = selectedOption;
        }

        public Long getFlashCardID() {
            return flashCardID;
        }

        public void setFlashCardID(Long flashCardID) {
            this.flashCardID = flashCardID;
        }

        public String getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(String selectedOption) {
            this.selectedOption = selectedOption;
        }
    }
}