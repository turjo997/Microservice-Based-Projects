package com.example.UserService.controllers;

import com.example.UserService.entity.UserEntity;
import com.example.UserService.model.UserRequestModel;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register-user")
    public ResponseEntity<Object> registerUser(@RequestBody UserRequestModel userRequestModel) {
        UserEntity userEntity = userService.registerUser(userRequestModel);

        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

    @GetMapping("/user-balance")
    public Long getBalance(@RequestParam("userId") Long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        return userEntity.getBalance();
    }

    @PutMapping("/update-balance")
    public void updateBalance(@RequestParam("userId") Long userId, @RequestParam("currentBalance") Long currentBalance) {
        UserEntity userFromDB = userRepository.findById(userId).get();
        userFromDB.setBalance(currentBalance);
        userRepository.save(userFromDB);
    }

}
