package com.bjit.tss.service;

import com.bjit.tss.model.ApprovalModel;
import org.springframework.http.ResponseEntity;

public interface ApprovalService {
    ResponseEntity<Object> createApproval(ApprovalModel approvalModel);

    ResponseEntity<Object> updateApproval(Long approvalId, ApprovalModel approvalModel);

    ResponseEntity<Object> deleteApproval(Long approvalId);

    ResponseEntity<Object> getAllApprovals();

    ResponseEntity<Object> getApprovalById(Long approvalId);
}
