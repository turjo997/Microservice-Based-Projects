package com.bjit.tss.controllers;

import com.bjit.tss.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resource/applicant")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;


    @PostMapping("/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> saveResourceByApplicantId(@PathVariable Long applicantId, @RequestBody MultipartFile photo, @RequestBody MultipartFile cv) {
        return resourceService.saveResourceByApplicantId(applicantId, photo, cv);
    }

    @GetMapping("/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getResourceByApplicantId(@PathVariable Long applicantId) {
        return resourceService.getResourceByApplicantId(applicantId);
    }

    @DeleteMapping("/{applicantId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> deleteResourceByApplicantId(@PathVariable Long applicantId) {
        return resourceService.deleteResourceByApplicantId(applicantId);
    }
}
