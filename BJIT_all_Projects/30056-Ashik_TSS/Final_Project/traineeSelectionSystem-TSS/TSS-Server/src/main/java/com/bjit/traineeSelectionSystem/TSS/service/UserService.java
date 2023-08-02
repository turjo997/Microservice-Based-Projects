package com.bjit.traineeSelectionSystem.TSS.service;

import com.bjit.traineeSelectionSystem.TSS.model.ResponseModel;
import com.bjit.traineeSelectionSystem.TSS.model.User.LoginRequest;
import com.bjit.traineeSelectionSystem.TSS.model.User.UserRequest;
import org.springframework.http.ResponseEntity;;

public interface UserService {
    ResponseEntity<ResponseModel> register(UserRequest userRequest);

    ResponseEntity<ResponseModel> login(LoginRequest loginRequest);

}
