package com.bjit.tss.controllers;

import com.bjit.tss.model.ExamModel;
import com.bjit.tss.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> createExam(@RequestBody ExamModel examModel) {
        return examService.createExam(examModel);
    }

    @PutMapping("/code/{examCode}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> updateExamByExamCode(@PathVariable Long examCode, @RequestBody ExamModel examModel) {
        return examService.updateExamByExamCode(examCode, examModel);
    }

    @DeleteMapping("/code/{examCode}")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> deleteExamByExamCode(@PathVariable Long examCode) {
        return examService.deleteExamByExamCode(examCode);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{examId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getExamById(@PathVariable Long examId) {
        return examService.getExamById(examId);
    }

    @GetMapping("/code/{examCode}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getExamByExamCode(@PathVariable Long examCode) {
        return examService.getExamByExamCode(examCode);
    }
}
