package com.suffixIT.springsecurity.controllers;

import com.suffixIT.springsecurity.model.SignInRequest;
import com.suffixIT.springsecurity.model.AuthenticationResponse;
import com.suffixIT.springsecurity.model.SignUpRequestModel;
import com.suffixIT.springsecurity.service.AuthenticationService;
import com.suffixIT.springsecurity.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody SignUpRequestModel requestModel){
        return authenticationService.signup(requestModel);
    }

    private final AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(authenticationService.signin(signInRequest), HttpStatus.OK);
    }

}
