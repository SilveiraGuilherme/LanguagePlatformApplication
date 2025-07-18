package com.project.languageplatform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.languageplatform.entity.PracticeSession;
import com.project.languageplatform.repository.PracticeSessionRepository;
import com.project.languageplatform.service.PracticeSessionService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PracticeSessionServiceTest {

    @Mock
    private PracticeSessionRepository practiceSessionRepository;

    @InjectMocks
    private PracticeSessionService practiceSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPracticeSessions() {
        PracticeSession session1 = new PracticeSession();
        PracticeSession session2 = new PracticeSession();
        when(practiceSessionRepository.findAll()).thenReturn(Arrays.asList(session1, session2));

        List<PracticeSession> result = practiceSessionService.getAllPracticeSessions();
        assertEquals(2, result.size());
        verify(practiceSessionRepository, times(1)).findAll();
    }

    @Test
    void testGetPracticeSessionById() {
        PracticeSession session = new PracticeSession();
        when(practiceSessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Optional<PracticeSession> result = practiceSessionService.getPracticeSessionById(1L);
        assertEquals(session, result.orElse(null));
        verify(practiceSessionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOngoingSessionByStudentId() {
        PracticeSession session = new PracticeSession();
        when(practiceSessionRepository.findByUserUserIDAndSessionStatus(1L, PracticeSession.SessionStatus.ONGOING)).thenReturn(Optional.of(session));

        Optional<PracticeSession> result = practiceSessionService.getOngoingSessionByStudentId(1L);
        assertEquals(session, result.orElse(null));
        verify(practiceSessionRepository, times(1)).findByUserUserIDAndSessionStatus(1L, PracticeSession.SessionStatus.ONGOING);
    }

    @Test
    void testSavePracticeSession() {
        PracticeSession session = new PracticeSession();
        when(practiceSessionRepository.save(session)).thenReturn(session);

        PracticeSession result = practiceSessionService.savePracticeSession(session);
        assertEquals(session, result);
        verify(practiceSessionRepository, times(1)).save(session);
    }

@Test
void testUpdatePracticeSession() {
    Long sessionId = 1L;

    PracticeSession existingSession = new PracticeSession();
    existingSession.setSessionStatus(PracticeSession.SessionStatus.ONGOING);

    PracticeSession updatedSession = new PracticeSession();
    updatedSession.setSessionStatus(PracticeSession.SessionStatus.ONGOING); // simulate update

    // Mock the findById call to return an existing session
    when(practiceSessionRepository.findById(sessionId)).thenReturn(Optional.of(existingSession));

    // Mock the save call to return the updated session
    when(practiceSessionRepository.save(existingSession)).thenReturn(existingSession);

    PracticeSession result = practiceSessionService.updatePracticeSession(sessionId, updatedSession);

    assertEquals(existingSession, result);
    verify(practiceSessionRepository).findById(sessionId);
    verify(practiceSessionRepository).save(existingSession);
}

    @Test
    void testDeletePracticeSession() {
        Long id = 1L;
        doNothing().when(practiceSessionRepository).deleteById(id);

        practiceSessionService.deletePracticeSession(id);
        verify(practiceSessionRepository, times(1)).deleteById(id);
    }
}
