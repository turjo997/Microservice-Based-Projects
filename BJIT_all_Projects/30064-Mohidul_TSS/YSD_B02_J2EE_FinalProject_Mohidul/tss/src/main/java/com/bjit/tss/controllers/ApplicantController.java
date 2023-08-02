package com.bjit.tss.controllers;

import com.bjit.tss.model.ApplicantModel;
import com.bjit.tss.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> createApplicant(@RequestBody ApplicantModel applicantModel) {
        return applicantService.createApplicant(applicantModel);
    }

    @PutMapping("/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> updateApplicant(@PathVariable Long applicantId, @RequestBody ApplicantModel applicantModel) {
        return applicantService.updateApplicant(applicantId, applicantModel);
    }

    @DeleteMapping("/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> deleteApplicant(@PathVariable Long applicantId) {
        return applicantService.deleteApplicant(applicantId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getApplicantById(@PathVariable Long applicantId) {
        return applicantService.getApplicantById(applicantId);
    }
}
