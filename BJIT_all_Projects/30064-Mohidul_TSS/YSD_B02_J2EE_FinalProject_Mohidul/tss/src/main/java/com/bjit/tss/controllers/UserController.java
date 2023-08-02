package com.bjit.tss.controllers;

import com.bjit.tss.model.AuthenticationRequest;
import com.bjit.tss.model.AuthenticationResponse;
import com.bjit.tss.model.UserRequestModel;
import com.bjit.tss.service.Implementation.AuthenticationService;
import com.bjit.tss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequestModel requestModel) {
        return userService.register(requestModel);
    }

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }

}
