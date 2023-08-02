package com.bjit.tss.controllers;

import com.bjit.tss.model.MarkModel;
import com.bjit.tss.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mark")
@RequiredArgsConstructor
public class MarkController {
    private final MarkService markService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> saveMark(@RequestBody MarkModel markModel) {
        return markService.saveMark(markModel);
    }

    @PutMapping("/update/{markId}")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> updateMark(@PathVariable Long markId, @RequestBody MarkModel markModel) {
        return markService.updateMark(markId, markModel);
    }

    @GetMapping("/{markId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getMarkById(@PathVariable Long markId) {
        return markService.getMarkById(markId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getAllMarks() {
        return markService.getAllMarks();
    }

    @GetMapping("/applicant/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getMarkByApplicantId(@PathVariable Long applicantId) {
        return markService.getMarkByApplicantId(applicantId);
    }

    @GetMapping("/circular/{circular}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getMarkByCircular(@PathVariable String circular) {
        return markService.getMarkByCircular(circular);
    }

    @DeleteMapping("/delete/{markId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> deleteMark(@PathVariable Long markId) {
        return markService.deleteMark(markId);
    }
}
