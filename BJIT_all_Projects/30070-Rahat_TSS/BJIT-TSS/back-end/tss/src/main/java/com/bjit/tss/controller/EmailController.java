package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.EmailRequest;
import com.bjit.tss.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<?>> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        return emailService.sendEmail(emailRequest);
    }
}