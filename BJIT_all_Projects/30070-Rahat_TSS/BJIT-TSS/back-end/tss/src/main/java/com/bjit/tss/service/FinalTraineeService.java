package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.FinalTraineeSelectionRequest;
import org.springframework.http.ResponseEntity;

public interface FinalTraineeService {
    
    ResponseEntity<ApiResponse<?>> allPassedFinalTrainee(String batchCode);

    ResponseEntity<ApiResponse<?>> selectFinalTrainee(FinalTraineeSelectionRequest request);

    ResponseEntity<ApiResponse<?>> allFinalTrainee(String batchCode);
}