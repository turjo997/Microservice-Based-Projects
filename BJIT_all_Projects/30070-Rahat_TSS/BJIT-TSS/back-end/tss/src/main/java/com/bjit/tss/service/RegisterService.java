package com.bjit.tss.service;

import com.bjit.tss.model.request.EvaluatorRegisterRequest;
import com.bjit.tss.model.request.RegisterRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.ValidationRequest;
import org.springframework.http.ResponseEntity;

public interface RegisterService {

    ResponseEntity<ApiResponse<?>> applicantRegistration(RegisterRequest registerRequest);

    ResponseEntity<ApiResponse<?>> adminRegistration(String email, String password);

    ResponseEntity<ApiResponse<?>> mailValidation(ValidationRequest validationRequest);

    ResponseEntity<ApiResponse<?>> evaluatorRegistration(EvaluatorRegisterRequest evaluatorRegisterRequest);

    ResponseEntity<ApiResponse<?>> sendEmailVerification();
}