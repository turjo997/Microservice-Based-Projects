package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.QuestionNumberRequest;
import org.springframework.http.ResponseEntity;

public interface QuestionNumberService {

    ResponseEntity<ApiResponse<?>> setWrittenQuestionNumber(QuestionNumberRequest questionNumberRequest);
}