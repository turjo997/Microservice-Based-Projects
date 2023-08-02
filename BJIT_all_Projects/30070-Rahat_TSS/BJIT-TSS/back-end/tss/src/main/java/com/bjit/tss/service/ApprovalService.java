package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.ApprovalRequest;
import org.springframework.http.ResponseEntity;

public interface ApprovalService {

    ResponseEntity<ApiResponse<?>> approveApplicant(ApprovalRequest approvalRequest);
}
