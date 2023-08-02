package com.bjit.tss.service;

import com.bjit.tss.model.request.LoginRequest;
import com.bjit.tss.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<ApiResponse<?>> login(LoginRequest loginRequest);

    ResponseEntity<ApiResponse<?>> validation();
}