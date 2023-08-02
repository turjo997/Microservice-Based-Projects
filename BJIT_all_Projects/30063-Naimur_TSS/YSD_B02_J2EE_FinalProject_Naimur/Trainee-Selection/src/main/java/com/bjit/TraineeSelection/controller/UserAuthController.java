package com.bjit.TraineeSelection.controller;

import com.bjit.TraineeSelection.model.AuthenticationRequest;
import com.bjit.TraineeSelection.model.AuthenticationResponse;
import com.bjit.TraineeSelection.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/login")
public class UserAuthController {
    private final AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }


}
