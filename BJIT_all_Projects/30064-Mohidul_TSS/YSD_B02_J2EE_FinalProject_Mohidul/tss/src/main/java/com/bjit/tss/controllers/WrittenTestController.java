package com.bjit.tss.controllers;

import com.bjit.tss.model.WrittenTestModel;
import com.bjit.tss.service.WrittenTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/written")
@RequiredArgsConstructor
public class WrittenTestController {
    private final WrittenTestService writtenTestService;

    @GetMapping("/{writtenTestId}")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getWrittenTestById(@PathVariable Long writtenTestId) {
        return writtenTestService.getWrittenTestById(writtenTestId);
    }
    @GetMapping("/applicantId/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getWrittenTestByApplicantId(@PathVariable Long applicantId) {
        return writtenTestService.getWrittenTestByApplicantId(applicantId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getAllWrittenTest() {
        return writtenTestService.getAllWrittenTest();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> createWrittenTest(@RequestBody WrittenTestModel writtenTestModel) {
        return writtenTestService.createWrittenTest(writtenTestModel);
    }

    @PutMapping("/{writtenTestId}")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> updateWrittenTest(@PathVariable Long writtenTestId, @RequestBody WrittenTestModel writtenTestModel) {
        return writtenTestService.updateWrittenTest(writtenTestId, writtenTestModel);
    }

    @DeleteMapping("/{writtenTestId}")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> deleteWrittenTest(@PathVariable Long writtenTestId) {
        return writtenTestService.deleteWrittenTest(writtenTestId);
    }

    @PostMapping("/auto-create")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> autoCreateWrittenTest() {
        return writtenTestService.autoCreateWrittenTest();
    }


    @PutMapping("/update/{hiddenCode}")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> updateWrittenTestByHiddenCode(@PathVariable Long hiddenCode, @RequestBody WrittenTestModel writtenTestModel) {
        return writtenTestService.updateWrittenTest(hiddenCode, writtenTestModel.getMark());
    }

    @PutMapping("/upload")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> updateWrittenTestByHiddenCode(@RequestParam("file") MultipartFile file) {
        return writtenTestService.uploadWrittenTestByHiddenCode(file);
    }


}
