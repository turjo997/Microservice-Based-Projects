package com.example.user.demouser.service.impl;

import com.example.user.demouser.entity.UserEntity;
import com.example.user.demouser.model.UserResponse;
import com.example.user.demouser.model.UserSecondResponse;
import com.example.user.demouser.repository.UserRespository;
import com.example.user.demouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;
    @Override
    public UserResponse getUserById(Long id) {
        Optional<UserEntity> user = userRespository.findById(id);
        if(user.isPresent()){
            UserEntity requiredUser = user.get();
            Integer balance = requiredUser.getAccountBalance();
            UserResponse response = UserResponse.builder()
                    .accountBalance(balance).build();
            return response;
        }
        UserResponse response = UserResponse.builder().accountBalance(0).build();
        return response;
    }

    @Override
    public void updateUserBalance(Integer balance, Long id) {
        Optional<UserEntity> user = userRespository.findById(id);
        if(user.isPresent()){
            UserEntity requiredUser = user.get();
            Integer userBalance = requiredUser.getAccountBalance();

            Integer updatedAmount = userBalance-balance;
            requiredUser.setAccountBalance(updatedAmount);
            userRespository.save(requiredUser);
        }
    }

    @Override
    public List<UserSecondResponse> getAllUsers(){
        List<UserEntity> users = userRespository.findAll();
        List<UserSecondResponse> responseUsers = new ArrayList<>();
        for(UserEntity user: users){
            UserSecondResponse response = UserSecondResponse.builder()
                    .userName(user.getUserName())
                    .accountBalance(user.getAccountBalance()).build();
            responseUsers.add(response);
        }
        return responseUsers;
    }
}
