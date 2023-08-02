package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.assignment.*;
import com.bjit.trainingmanagementsystem.service.assignment.AssignmentService;
import com.bjit.trainingmanagementsystem.service.assignment.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;

    @PostMapping("/create")
    public ResponseEntity<AssignmentResponseModel> create(@ModelAttribute  AssignmentCreateRequest assignmentCreateRequest) {
        return new ResponseEntity<>(assignmentService.create(assignmentCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<AssignmentResponseModel>> getAssignmentsByBatchId(@PathVariable Long batchId) {
        List<AssignmentResponseModel> assignments = assignmentService.findAssignmentsByBatchId(batchId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseModel> findAssignmentByAssignmentId(@PathVariable Long assignmentId) {
        AssignmentResponseModel assignmentResponseModel = assignmentService.findAssignmentById(assignmentId);
        return new ResponseEntity<>(assignmentResponseModel, HttpStatus.OK);
    }

    @PutMapping("/update/{assignmentId}")
    public ResponseEntity<AssignmentResponseModel> updateCourse(@PathVariable Long assignmentId, @ModelAttribute AssignmentUpdateModel assignmentUpdateModel) {
        return new ResponseEntity<>(assignmentService.updateAssignment(assignmentUpdateModel, assignmentId), HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResponseModel> submit(@ModelAttribute SubmissionCreateRequest submissionCreateRequest){
        return new ResponseEntity<>(submissionService.createSubmission(submissionCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<SubmissionResponseModel> getBySubmissionId(@PathVariable Long submissionId) {
        SubmissionResponseModel submissionResponseModel = submissionService.getSubmissionById(submissionId);
        return new ResponseEntity<>(submissionResponseModel, HttpStatus.OK);
    }

    @GetMapping("/submission-list/{assignmentId}")
    public ResponseEntity<List<SubmissionResponseModel>> getSubmissionsByAssignmentId(@PathVariable Long assignmentId) {
        List<SubmissionResponseModel> submissionResponseModels = submissionService.getSubmissionsByAssignmentId(assignmentId);
        return new ResponseEntity<>(submissionResponseModels, HttpStatus.OK);
    }

    @GetMapping("/submission/{assignmentId}/trainee/{traineeId}")
    public ResponseEntity<SubmissionResponseModel> getTurnedInStatus(@PathVariable Long assignmentId, @PathVariable Long traineeId) {
        SubmissionResponseModel submissionResponseModel = submissionService.getTurnedInStatus(assignmentId, traineeId);
        return new ResponseEntity<>(submissionResponseModel, HttpStatus.OK);
    }


    @GetMapping("/download-file/{assignmentId}")
    public ResponseEntity<Resource> downloadAssignmentFile(@PathVariable Long assignmentId) {
        Resource resource = assignmentService.downloadAssignmentById(assignmentId);
        if (resource != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/{assignmentId}")
    public ResponseEntity<Resource> downloadAssignment(@PathVariable Long assignmentId) {
        Resource resource = assignmentService.getAssignmentFileResource(assignmentId);

        if (resource != null && resource.exists() && resource.isReadable()) {
            String filename = "assignment.zip";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/submission/download-file/{submissionId}")
    public ResponseEntity<Resource> downloadSubmissionFile(@PathVariable Long submissionId) {
        Resource resource = submissionService.downloadSubmissionById(submissionId);
        if (resource != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
