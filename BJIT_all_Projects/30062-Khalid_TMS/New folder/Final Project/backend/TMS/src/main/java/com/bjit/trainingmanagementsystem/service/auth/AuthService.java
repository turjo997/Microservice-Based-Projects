package com.bjit.trainingmanagementsystem.service.auth;

import com.bjit.trainingmanagementsystem.models.auth.AuthenticationResponse;
import com.bjit.trainingmanagementsystem.models.auth.LoginRequest;
import com.bjit.trainingmanagementsystem.models.auth.RegisterRequest;

import java.io.IOException;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse login(LoginRequest loginRequest);
}
