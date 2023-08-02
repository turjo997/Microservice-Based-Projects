package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.ProfileDTO;
import com.bjit.finalproject.TMS.dto.UserRequestDTO;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.FileDirectoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final FileDirectoryServices fileDirectoryServices;
    @PostMapping("/registerUser")
    public ResponseEntity<Object> createTrainee(@ModelAttribute UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.registerUser(userRequestDTO), HttpStatus.OK);
    }
    @GetMapping("/get-all-users")
    public ResponseEntity<Object> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/get-all-trainees/{roleName}")
    public ResponseEntity<Object> getAllTrainees(@PathVariable String roleName){
        return new ResponseEntity<>(userService.getAllTrainees(roleName), HttpStatus.OK);
    }
    @GetMapping("/get-all-trainers/{roleName}")
    public ResponseEntity<Object> getAllTrainers(@PathVariable String roleName){
        return new ResponseEntity<>(userService.getAllTrainers(roleName), HttpStatus.OK);
    }
    @GetMapping("/get-user-profile")
    public ResponseEntity<Object> getUserProfile(){
        return new ResponseEntity<>(userService.getUserProfile(), HttpStatus.OK);
    }
    @PutMapping("/update-profile")
    public ResponseEntity<Object> updateProfile(@ModelAttribute ProfileDTO profileDTO){
        return new ResponseEntity<>(userService.updateProfile(profileDTO), HttpStatus.OK);
    }
    @GetMapping("/get-user-role")
    public ResponseEntity<Object> getUserRole(){
        return new ResponseEntity<>(userService.getUserRole(), HttpStatus.OK);
    }
    @GetMapping("/get-current-user")
    public ResponseEntity<Object> getCurrentUser(){
        return new ResponseEntity<>(userService.loggedInUserEmail(), HttpStatus.OK);
    }
    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        return fileDirectoryServices.getImage(fileName);
    }

}
