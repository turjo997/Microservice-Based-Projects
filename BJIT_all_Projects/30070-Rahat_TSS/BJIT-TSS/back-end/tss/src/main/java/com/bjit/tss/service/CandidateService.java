package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CandidateService {

    ResponseEntity<ApiResponse<?>> allCandidates();
}