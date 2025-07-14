package com.guilherme.project.languageplatform;

import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.repository.QuizResultRepository;
import com.guilherme.project.languageplatform.repository.FlashCardRepository;
import com.guilherme.project.languageplatform.repository.StudentRepository;
import com.guilherme.project.languageplatform.repository.PracticeSessionRepository;
import com.guilherme.project.languageplatform.service.QuizResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizResultServiceTest {

    private QuizResultRepository quizResultRepository;
    private FlashCardRepository flashCardRepository;
    private StudentRepository studentRepository;
    private PracticeSessionRepository practiceSessionRepository;
    private QuizResultService quizResultService;

    @BeforeEach
    public void setUp() {
        quizResultRepository = mock(QuizResultRepository.class);
        flashCardRepository = mock(FlashCardRepository.class);
        studentRepository = mock(StudentRepository.class);
        practiceSessionRepository = mock(PracticeSessionRepository.class);

        quizResultService = new QuizResultService();
        quizResultService.quizResultRepository = quizResultRepository;
        quizResultService.flashCardRepository = flashCardRepository;
        quizResultService.studentRepository = studentRepository;
        quizResultService.practiceSessionRepository = practiceSessionRepository;
    }

    @Test
    public void testGetAllQuizResults() {
        QuizResult quizResult = new QuizResult();
        when(quizResultRepository.findAll()).thenReturn(Arrays.asList(quizResult));

        List<QuizResult> results = quizResultService.getAllQuizResults();

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(quizResultRepository, times(1)).findAll();
    }

    @Test
    public void testSaveQuizResult() {
        QuizResult quizResult = new QuizResult();
        when(quizResultRepository.save(quizResult)).thenReturn(quizResult);

        QuizResult saved = quizResultService.saveQuizResult(quizResult);

        assertNotNull(saved);
        verify(quizResultRepository, times(1)).save(quizResult);
    }

    @Test
    public void testDeleteQuizResultById() {
        Long id = 1L;
        when(quizResultRepository.existsById(id)).thenReturn(true);
        doNothing().when(quizResultRepository).deleteById(id);

        quizResultService.deleteQuizResultById(id);

        verify(quizResultRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetQuizResultById() {
        Long id = 1L;
        QuizResult quizResult = new QuizResult();
        when(quizResultRepository.findById(id)).thenReturn(Optional.of(quizResult));

        QuizResult result = quizResultService.getQuizResultById(id);

        assertNotNull(result);
        verify(quizResultRepository, times(1)).findById(id);
    }
}