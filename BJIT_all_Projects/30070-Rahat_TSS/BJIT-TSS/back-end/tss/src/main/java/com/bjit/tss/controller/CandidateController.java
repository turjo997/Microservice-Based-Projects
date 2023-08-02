package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.service.AdmitCardService;
import com.bjit.tss.service.ApplicantDashboardService;
import com.bjit.tss.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/candidate")
public class CandidateController {

    private final AdmitCardService admitCardService;
    private final CandidateService candidateService;
    private final ApplicantDashboardService applicantDashboardService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> allCandidates() {
        return candidateService.allCandidates();
    }

    @GetMapping("/generate-admit/{examineeId}")
    public ResponseEntity<InputStreamResource> generateAdmitCard(@PathVariable String examineeId) {

        ByteArrayInputStream pdf = admitCardService.generateAdmit(examineeId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Description", "inline;file=AdmitCard.pdf");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<?>> getApplicantDashboardData(){
        return applicantDashboardService.getApplicantDashboardData();
    }


}