package com.example.AuthenticationService.controllers;

import com.example.AuthenticationService.models.AccessResponse;
import com.example.AuthenticationService.models.LoginRequest;
import com.example.AuthenticationService.models.RegisterRequest;
import com.example.AuthenticationService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AccessResponse<String>> register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<AccessResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
