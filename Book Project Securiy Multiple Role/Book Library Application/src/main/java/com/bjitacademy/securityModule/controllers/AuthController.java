package com.bjitacademy.securityModule.controllers;

import com.bjitacademy.securityModule.model.AuthenticationRequest;
import com.bjitacademy.securityModule.model.AuthenticationResponse;
import com.bjitacademy.securityModule.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }
}
