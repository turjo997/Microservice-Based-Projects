package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.assignment.AssignmentAnswerRequestDTO;
import com.bjit.finalproject.TMS.dto.assignment.AssignmentRequestDTO;
import com.bjit.finalproject.TMS.dto.assignment.AssignmentUpdateDTO;
import com.bjit.finalproject.TMS.service.AssignmentService;
import com.bjit.finalproject.TMS.utils.FileDirectoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final FileDirectoryServices fileDirectoryServices;
    @PostMapping("/create-assignment")
    public ResponseEntity<Object> createAssignment(@ModelAttribute AssignmentRequestDTO assignmentRequestDTO){
        return new ResponseEntity<>(assignmentService.createAssignment(assignmentRequestDTO), HttpStatus.OK);
    }
    @PutMapping("/update-assignment/{assignmentId}")
    public ResponseEntity<Object> updateAssignment(@PathVariable Long assignmentId, @ModelAttribute AssignmentUpdateDTO updateDTO){
        return new ResponseEntity<>(assignmentService.updateAssignment(assignmentId, updateDTO), HttpStatus.OK);
    }
    @GetMapping("/get-assignments-by-user")
    public ResponseEntity<Object>getAssignments(){
        return new ResponseEntity<>(assignmentService.getAssignments(), HttpStatus.OK);
    }
    @GetMapping("/get-assignments/{scheduleId}")
    public ResponseEntity<Object> getAssignmentsBySchedules(@PathVariable Long scheduleId){
        return new ResponseEntity<>(assignmentService.getAssignmentsBySchedules(scheduleId), HttpStatus.OK);
    }
    @PostMapping("/submit-answer")
    public ResponseEntity<Object> submitAnswer(@ModelAttribute AssignmentAnswerRequestDTO answerDTO){
        return new ResponseEntity<>(assignmentService.submitAnswer(answerDTO), HttpStatus.OK);
    }
    @GetMapping("/get-assignment-submissions/{assignmentId}")
    public ResponseEntity<Object> getAssignmentSubmissions(@PathVariable Long assignmentId){
        return new ResponseEntity<>(assignmentService.getAssignmentSubmissions(assignmentId), HttpStatus.OK);
    }
    @GetMapping("/files/{fileName:.+}")//
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        return fileDirectoryServices.getFile(fileName);
    }

}
