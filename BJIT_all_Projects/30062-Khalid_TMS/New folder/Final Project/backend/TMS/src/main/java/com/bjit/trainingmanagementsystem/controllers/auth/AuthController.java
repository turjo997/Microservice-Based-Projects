package com.bjit.trainingmanagementsystem.controllers.auth;

import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.auth.AuthenticationResponse;
import com.bjit.trainingmanagementsystem.models.auth.LoginRequest;
import com.bjit.trainingmanagementsystem.models.auth.RegisterRequest;
import com.bjit.trainingmanagementsystem.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@ModelAttribute RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
