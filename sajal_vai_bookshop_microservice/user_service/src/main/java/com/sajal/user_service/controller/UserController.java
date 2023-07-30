package com.sajal.user_service.controller;

import com.sajal.user_service.dto.UserDto;
import com.sajal.user_service.entity.PurchasedBook;
import com.sajal.user_service.entity.UserEntity;
import com.sajal.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create user
    @PostMapping("/user")
    ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    // get All user
    @GetMapping("/users")
    ResponseEntity<List<UserEntity>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }
    // get user
    @GetMapping("/user/{id}")
    ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    //for service communication
    @GetMapping("/user/exist/{id}")
    Boolean userExist(@PathVariable Long id){
        return userService.checkUserExist(id);
    }

    //update user
    @PutMapping("/user")
    ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user));
    }

    // Delete user
    @DeleteMapping("/user/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //Purchase book

    //add book to user
    @PostMapping("/user/add-book")
    Boolean addBookToUser(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam Integer quantity){
        return userService.addBookToUser(userId,bookId,quantity);
    }

}
