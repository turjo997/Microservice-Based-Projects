package com.example.demo.controller;

import com.example.demo.entity.Submission;
import com.example.demo.model.*;
import com.example.demo.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor

public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/create/{taskId}/{traineeId}")
    public ResponseEntity<Submission> createNewSubmission(@RequestParam("answer") MultipartFile answer , @ModelAttribute TaskSubmissionModel submit , @PathVariable Long taskId , @PathVariable Long traineeId) throws IOException {
        return submissionService.createSubmission(answer , submit , taskId , traineeId);
    }

}
