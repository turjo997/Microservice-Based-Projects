package com.example.UserService.service;

import com.example.UserService.entity.UserEntity;
import com.example.UserService.model.UserRequestModel;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    public UserEntity registerUser(UserRequestModel userRequestModel) {

        UserEntity userEntity = UserEntity.builder()
                .name(userRequestModel.getName())
                .balance(userRequestModel.getBalance())
                .email(userRequestModel.getEmail())
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);


        return savedUserEntity;
    }
}
