package com.bjit.finalproject.TMS;

import com.bjit.finalproject.TMS.dto.assignment.AssignmentAnswerResponse;
import com.bjit.finalproject.TMS.model.Assignment;
import com.bjit.finalproject.TMS.model.AssignmentAnswer;
import com.bjit.finalproject.TMS.repository.AssignmentAnswerRepository;
import com.bjit.finalproject.TMS.repository.AssignmentRepository;
import com.bjit.finalproject.TMS.service.impl.AssignmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentTest {

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private AssignmentAnswerRepository assignmentAnswerRepo;

    @Test
    public void testGetAssignmentSubmissions_Success() {
        Long assignmentId = 1L;

        // Mock the Assignment object returned by assignmentRepository.findById
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(assignmentId);
        assignment.setAssignmentTitle("Assignment 1");

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        // Mock the list of AssignmentAnswer objects returned by assignmentAnswerRepo.findByAssignment
        List<AssignmentAnswer> assignmentAnswers = new ArrayList<>();
        AssignmentAnswer answer1 = new AssignmentAnswer();
        answer1.setAnswerId(101L);
        answer1.setAssignment(assignment);
        answer1.setAssignmentAnswerFile("answer1.txt");
        answer1.setTraineeEmail("trainee1@example.com");

        AssignmentAnswer answer2 = new AssignmentAnswer();
        answer2.setAnswerId(102L);
        answer2.setAssignment(assignment);
        answer2.setAssignmentAnswerFile("answer2.txt");
        answer2.setTraineeEmail("trainee2@example.com");

        assignmentAnswers.add(answer1);
        assignmentAnswers.add(answer2);

        when(assignmentAnswerRepo.findByAssignment(assignment)).thenReturn(assignmentAnswers);

        // Perform the method call
        List<AssignmentAnswerResponse> responses = assignmentService.getAssignmentSubmissions(assignmentId);

        // Assertions
        assertNotNull(responses);
        assertEquals(2, responses.size());

        // Verify the properties of the AssignmentAnswerResponse objects
        AssignmentAnswerResponse response1 = responses.get(0);
        assertEquals(101L, response1.getAnswerId());
        assertEquals("answer1.txt", response1.getAnswerFile());
        assertEquals("trainee1@example.com", response1.getSubmittedBy());
        assertEquals("Assignment 1", response1.getAssignmentTitle());

        AssignmentAnswerResponse response2 = responses.get(1);
        assertEquals(102L, response2.getAnswerId());
        assertEquals("answer2.txt", response2.getAnswerFile());
        assertEquals("trainee2@example.com", response2.getSubmittedBy());
        assertEquals("Assignment 1", response2.getAssignmentTitle());

        // Verify that the required methods were called
        verify(assignmentRepository, times(1)).findById(assignmentId);
        verify(assignmentAnswerRepo, times(1)).findByAssignment(assignment);
    }

    @Test
    public void testGetAssignmentSubmissions_AssignmentNotFound() {
        Long assignmentId = 1L;

        // Mock the assignmentRepository.findById to return an empty optional
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        // Perform the method call and assert that it throws NameNotFoundException

    }
}