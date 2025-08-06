package com.project.languageplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.languageplatform.dto.QuizResultRequestDTO;
import com.project.languageplatform.dto.QuizResultResponseDTO;
import com.project.languageplatform.entity.QuizResult;
import com.project.languageplatform.entity.User;
import com.project.languageplatform.entity.PracticeSession;
import com.project.languageplatform.repository.UserRepository;
import com.project.languageplatform.repository.PracticeSessionRepository;
import com.project.languageplatform.repository.QuizResultRepository;
import com.project.languageplatform.service.QuizResultService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/quiz-results")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PracticeSessionRepository practiceSessionRepository;
    @Autowired
    private QuizResultRepository quizResultRepository;

    // Fetch all quiz results
    @GetMapping
    public List<QuizResultResponseDTO> getAllQuizResults() {
        return quizResultService.getAllQuizResults().stream().map(result -> {
            QuizResultResponseDTO dto = new QuizResultResponseDTO();
            dto.setResultID(result.getResultID());
            dto.setStudentID(result.getUser().getUserID());
            dto.setStudentName(result.getUser().getFirstName() + " " + result.getUser().getLastName());
            dto.setSessionID(result.getSession().getSessionID());
            dto.setDifficultyLevel(result.getDifficultyLevel());
            dto.setTotalQuestions(result.getTotalQuestions());
            dto.setCorrectAnswers(result.getCorrectAnswers());
            dto.setScorePercentage(result.getScorePercentage());
            dto.setCompletionTime(result.getCompletionTime());
            return dto;
        }).toList();
    }

    // Fetch a single quiz result by its ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizResultResponseDTO> getQuizResultById(@PathVariable Long id) {
        QuizResult result = quizResultService.getQuizResultById(id);
        QuizResultResponseDTO dto = new QuizResultResponseDTO();
        dto.setResultID(result.getResultID());
        dto.setStudentID(result.getUser().getUserID());
        dto.setStudentName(result.getUser().getFirstName() + " " + result.getUser().getLastName());
        dto.setSessionID(result.getSession().getSessionID());
        dto.setDifficultyLevel(result.getDifficultyLevel());
        dto.setTotalQuestions(result.getTotalQuestions());
        dto.setCorrectAnswers(result.getCorrectAnswers());
        dto.setScorePercentage(result.getScorePercentage());
        dto.setCompletionTime(result.getCompletionTime());
        return ResponseEntity.ok(dto);
    }

    // Fetch all quiz results by user ID
    @GetMapping("/user/{userID}")
    public List<QuizResultResponseDTO> getQuizResultsByUserID(@PathVariable Long userID) {
        return quizResultService.getQuizResultsByUserId(userID);
    }

    // Save multiple quiz results
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<QuizResult>> createMultipleQuizResults(@RequestBody List<QuizResult> quizResults) {
        List<QuizResult> savedResults = quizResults.stream()
                .map(quizResultService::saveQuizResult)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResults);
    }

    // Submit a quiz result (used at end of quiz session)
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitQuizResult(@RequestBody QuizResultRequestDTO requestDTO) {
        Long userId = requestDTO.getUser().getUserID();
        Long sessionId = requestDTO.getSession().getSessionID();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        PracticeSession session = practiceSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));

        QuizResult result = new QuizResult();
        result.setUser(user);
        result.setSession(session);
        result.setDifficultyLevel(requestDTO.getDifficultyLevel());
        result.setTotalQuestions(requestDTO.getTotalQuestions());
        result.setCorrectAnswers(requestDTO.getCorrectAnswers());
        result.setScorePercentage(requestDTO.getScorePercentage());
        result.setCompletionTime(requestDTO.getCompletionTime());

        QuizResult savedResult = quizResultRepository.save(result);

        Map<String, Object> response = new HashMap<>();
        response.put("resultID", savedResult.getResultID());

        return ResponseEntity.ok(response);
    }

    // Update quiz result by ID
    @PutMapping("/{id}")
    public ResponseEntity<QuizResult> updateQuizResult(@PathVariable Long id, @RequestBody QuizResult updatedResult) {
        updatedResult.setResultID(id);
        QuizResult updated = quizResultService.saveQuizResult(updatedResult);
        return ResponseEntity.ok(updated);
    }

    // Delete quiz result by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizResult(@PathVariable Long id) {
        quizResultService.deleteQuizResultById(id);
        return ResponseEntity.noContent().build();
    }
}