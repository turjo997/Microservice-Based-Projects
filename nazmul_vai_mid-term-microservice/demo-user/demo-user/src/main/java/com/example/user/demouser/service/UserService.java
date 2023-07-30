package com.example.user.demouser.service;

import com.example.user.demouser.model.UserResponse;
import com.example.user.demouser.model.UserSecondResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserResponse getUserById(Long id);
    void updateUserBalance(Integer userResponse, Long id);
    List<UserSecondResponse> getAllUsers();
}
