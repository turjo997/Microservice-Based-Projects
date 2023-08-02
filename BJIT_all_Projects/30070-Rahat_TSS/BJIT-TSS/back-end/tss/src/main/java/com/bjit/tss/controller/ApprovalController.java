package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.ApprovalRequest;
import com.bjit.tss.service.ApprovalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/approval")
public class ApprovalController {

    private final ApprovalService approvalService;

    @PostMapping("/applicant")
    public ResponseEntity<ApiResponse<?>> markAs(@Valid @RequestBody ApprovalRequest approvalRequest) {
        return approvalService.approveApplicant(approvalRequest);
    }
}