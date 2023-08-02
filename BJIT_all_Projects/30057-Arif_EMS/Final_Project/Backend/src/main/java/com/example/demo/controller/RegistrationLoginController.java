package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class RegistrationLoginController {

    private final RegistrationLoginService authService;

    //All these methods are by default public, so no need to explicitly mention.
    @PostMapping("/register/admin")
    ResponseEntity<Object> createAdmin(@RequestParam("image") MultipartFile image , @ModelAttribute CreateAdmin admin) throws IOException {
        return authService.createAdmin(image , admin);
    }

    @PostMapping("/register/trainer")
    ResponseEntity<Object> createTrainer(@RequestParam("image") MultipartFile image , @ModelAttribute CreateTrainer trainer) throws IOException {
        return authService.createTrainer(image , trainer);
    }

    @PostMapping("/register/trainee")
    ResponseEntity<Object> createTrainee(@RequestParam("image") MultipartFile image , @ModelAttribute CreateTrainee trainee) throws IOException {
        return authService.createTrainee(image , trainee);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseModel> loginUser(@RequestBody LoginRequest login){
        return authService.loginUser(login);
    }

}
