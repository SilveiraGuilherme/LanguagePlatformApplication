package com.guilherme.project.languageplatform.service;
import com.guilherme.project.languageplatform.dto.QuizResultResponseDTO;

import java.util.Map;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.entity.Student;
import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.repository.FlashCardRepository;
import com.guilherme.project.languageplatform.repository.PracticeSessionRepository;
import com.guilherme.project.languageplatform.repository.StudentRepository;
import com.guilherme.project.languageplatform.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizResultService {

    @Autowired
    public QuizResultRepository quizResultRepository;

    @Autowired
    public FlashCardRepository flashCardRepository;

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public PracticeSessionRepository practiceSessionRepository;

    // Return all quiz results
    public List<QuizResult> getAllQuizResults() {
        return quizResultRepository.findAll();
    }

    // Save a new quiz result
    public QuizResult saveQuizResult(QuizResult result) {
        return quizResultRepository.save(result);
    }

    // Delete quiz result by ID
    public void deleteQuizResultById(Long id) {
        if (!quizResultRepository.existsById(id)) {
            throw new RuntimeException("QuizResult not found with ID: " + id);
        }
        quizResultRepository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    public QuizResultResponseDTO processQuizSubmission(Map<String, Object> submissionData) {
        Long studentID = ((Number) submissionData.get("studentID")).longValue();
        Long sessionID = ((Number) submissionData.get("sessionID")).longValue();
        List<Map<String, String>> answers = (List<Map<String, String>>) submissionData.get("answers");

        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Answer list cannot be null or empty.");
        }

        int totalQuestions = answers.size();
        int correctAnswers = 0;

        for (Map<String, String> answer : answers) {
            Object flashCardIdObj = answer.get("flashCardID");
            if (flashCardIdObj == null) {
                throw new RuntimeException("flashCardID is missing in one of the answers.");
            }
            final Long flashCardID;
            if (flashCardIdObj instanceof Number) {
                flashCardID = ((Number) flashCardIdObj).longValue();
            } else {
                try {
                    flashCardID = Long.valueOf(flashCardIdObj.toString());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid flashCardID: " + flashCardIdObj);
                }
            }

            Object selectedObj = answer.get("selectedOption");
            if (selectedObj == null) {
                throw new RuntimeException("selectedOption is missing in one of the answers.");
            }
            String selected = selectedObj.toString();

            FlashCard flashCard = flashCardRepository.findById(flashCardID)
                    .orElseThrow(() -> new RuntimeException("FlashCard not found: " + flashCardID));

            if (flashCard.getCorrectAnswer().equalsIgnoreCase(selected)) {
                correctAnswers++;
            }
        }

        BigDecimal scorePercentage = BigDecimal.valueOf((correctAnswers * 100.0) / totalQuestions)
                .setScale(2, RoundingMode.HALF_UP);

        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentID));

        PracticeSession session = practiceSessionRepository.findById(sessionID)
                .orElseThrow(() -> new RuntimeException("PracticeSession not found: " + sessionID));

        QuizResult result = new QuizResult();
        result.setStudent(student);
        result.setSession(session);
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setScorePercentage(scorePercentage);
        result.setCompletionTime(LocalDateTime.now());

        Object difficultyObj = submissionData.get("difficultyLevel");
        if (difficultyObj != null) {
            String difficultyStr = difficultyObj.toString().trim().toUpperCase();
            if (!difficultyStr.equals("MIXED")) {
                try {
                    result.setDifficultyLevel(Enum.valueOf(com.guilherme.project.languageplatform.enums.DifficultyLevel.class, difficultyStr));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid difficulty level: " + difficultyStr);
                }
            }
        } else {
            throw new RuntimeException("difficultyLevel is required.");
        }

        result = quizResultRepository.save(result);

        QuizResultResponseDTO response = new QuizResultResponseDTO();
        response.setResultID(result.getResultID());
        response.setStudentID(student.getStudentID());
        response.setStudentName(student.getFirstName() + " " + student.getLastName());
        response.setSessionID(session.getSessionID());
        response.setDifficultyLevel(result.getDifficultyLevel().toString());
        response.setTotalQuestions(result.getTotalQuestions());
        response.setCorrectAnswers(result.getCorrectAnswers());
        response.setScorePercentage(result.getScorePercentage());
        response.setCompletionTime(result.getCompletionTime());

        return response;
    }

    // Retrieve quiz result by ID
    public QuizResult getQuizResultById(Long id) {
        return quizResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizResult not found with ID: " + id));
    }
}