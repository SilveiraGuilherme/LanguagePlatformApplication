package com.guilherme.project.languageplatform.service;

import java.util.Map;
import java.util.stream.Collectors;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.entity.Student;
import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.enums.DifficultyLevel;
import com.guilherme.project.languageplatform.repository.FlashCardRepository;
import com.guilherme.project.languageplatform.repository.PracticeSessionRepository;
import com.guilherme.project.languageplatform.repository.StudentRepository;
import com.guilherme.project.languageplatform.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private FlashCardRepository flashCardRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    public List<QuizResult> getAllQuizResults() {
        return quizResultRepository.findAll();
    }

    public QuizResult saveQuizResult(QuizResult result) {
        return quizResultRepository.save(result);
    }

    public void deleteQuizResultById(Long id) {
        if (!quizResultRepository.existsById(id)) {
            throw new RuntimeException("QuizResult not found with ID: " + id);
        }
        quizResultRepository.deleteById(id);
    }

    public QuizResult processQuizSubmission(QuizSubmission submission) {
        Long studentID = submission.getStudentID();
        List<Answer> answers = submission.getAnswers();

        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Answer list cannot be null or empty.");
        }
        int totalQuestions = answers.size();
        int correctAnswers = 0;
        Set<String> difficultyLevels = new HashSet<>();

        for (Answer answer : answers) {
            Long flashCardID = answer.getFlashCardID();
            String selected = answer.getSelectedOption();

            FlashCard flashCard = flashCardRepository.findById(flashCardID)
                    .orElseThrow(() -> new RuntimeException("FlashCard not found: " + flashCardID));

            if (flashCard.getCorrectAnswer().equalsIgnoreCase(selected)) {
                correctAnswers++;
            }

            difficultyLevels.add(flashCard.getDifficultyLevel().name());
        }

        BigDecimal scorePercentage = BigDecimal.valueOf((correctAnswers * 100.0) / totalQuestions)
                .setScale(2, RoundingMode.HALF_UP);

        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentID));

        PracticeSession session = practiceSessionRepository.findById(submission.getSessionID())
                .orElseThrow(() -> new RuntimeException("PracticeSession not found: " + submission.getSessionID()));

        QuizResult result = new QuizResult();
        result.setStudent(student);
        result.setSession(session);
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setScorePercentage(scorePercentage);
        result.setCompletionTime(LocalDateTime.now());

        if (!difficultyLevels.isEmpty()) {
            String level = difficultyLevels.iterator().next();
            result.setDifficultyLevel(DifficultyLevel.valueOf(level));
        }

        return quizResultRepository.save(result);
    }

    public QuizResult getQuizResultById(Long id) {
        return quizResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizResult not found with ID: " + id));
    }
}