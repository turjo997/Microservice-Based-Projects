package com.bjit.tss.controllers;

import com.bjit.tss.model.ApprovalModel;
import com.bjit.tss.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;

    @PostMapping
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> createApproval(@RequestBody ApprovalModel approvalModel) {
        return approvalService.createApproval(approvalModel);
    }

    @PutMapping("/{approvalId}")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> updateApproval(@PathVariable Long approvalId, @RequestBody ApprovalModel approvalModel) {
        return approvalService.updateApproval(approvalId, approvalModel);
    }

    @DeleteMapping("/{approvalId}")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<Object> deleteApproval(@PathVariable Long approvalId, @RequestBody ApprovalModel approvalModel) {
        return approvalService.deleteApproval(approvalId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getAllApprovals() {
        return approvalService.getAllApprovals();
    }

    @GetMapping("/{approvalId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<Object> getApprovalsById(@PathVariable Long approvalId) {
        return approvalService.getApprovalById(approvalId);
    }
}
