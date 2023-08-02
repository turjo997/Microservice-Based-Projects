package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.EmailRequest;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<ApiResponse<?>> sendEmail(EmailRequest emailRequest);
}