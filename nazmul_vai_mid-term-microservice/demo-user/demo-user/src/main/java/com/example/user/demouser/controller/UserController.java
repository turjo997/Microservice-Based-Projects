package com.example.user.demouser.controller;

import com.example.user.demouser.model.UserResponse;
import com.example.user.demouser.model.UserSecondResponse;
import com.example.user.demouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/get-user-by-id/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/update-user-balance/{id}/{balance}")
    public void updateUserBalance(@PathVariable Integer balance, @PathVariable Long id){
        userService.updateUserBalance(balance, id);
    }

    @GetMapping("/get-users")
    public List<UserSecondResponse> getAllUsers(){
        return userService.getAllUsers();
    }
}
