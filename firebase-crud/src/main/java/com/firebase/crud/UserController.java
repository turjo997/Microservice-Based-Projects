package com.firebase.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody Users users) throws InterruptedException , ExecutionException {
        return userService.addUser(users);
    }

    @GetMapping("/get")
    public Users getUser(@RequestParam String documentId) throws InterruptedException , ExecutionException {
        return userService.getUser(documentId);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody Users users) throws InterruptedException , ExecutionException {
        return userService.updateUser(users);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam String documentId) throws InterruptedException , ExecutionException {
        return userService.deleteUser(documentId);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("API is hitting");
    }


}
