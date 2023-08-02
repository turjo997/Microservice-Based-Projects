package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.dto.assignment.*;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentService {
    AssignmentResponseDTO createAssignment(AssignmentRequestDTO assignmentRequestDTO);
    List<AssignFinalResponseDTO> getAssignments();
    List<AssignmentResponseDTO> getAssignmentsBySchedules(Long scheduleId);
    String updateAssignment(Long id, AssignmentUpdateDTO updateDTO);
    AssignmentAnswerResponse submitAnswer(AssignmentAnswerRequestDTO answerDTO);
    List<AssignmentAnswerResponse> getAssignmentSubmissions(Long assignmentId);
//    ResponseEntity<Resource> getFile(String fileName);
}
