package com.example.UserService.service;

import com.example.UserService.entity.UserEntity;
import com.example.UserService.model.UserRequestModel;


public interface UserService {
    UserEntity registerUser(UserRequestModel userRequestModel);
}
