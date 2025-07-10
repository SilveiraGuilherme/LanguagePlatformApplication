package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    public List<QuizResult> getAllQuizResults() {
        return quizResultRepository.findAll();
    }

    public Optional<QuizResult> getQuizResultById(Long id) {
        return quizResultRepository.findById(id);
    }

    public QuizResult saveQuizResult(QuizResult result) {
        return quizResultRepository.save(result);
    }

    public void deleteQuizResult(Long id) {
        quizResultRepository.deleteById(id);
    }
}