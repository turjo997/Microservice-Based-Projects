package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.authenticationDto.AuthenticationRequestDTO;
import com.bjit.finalproject.TMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class AuthController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequestDTO authReqModel){
        return new ResponseEntity<>(userService.login(authReqModel), HttpStatus.OK);
    }

}
