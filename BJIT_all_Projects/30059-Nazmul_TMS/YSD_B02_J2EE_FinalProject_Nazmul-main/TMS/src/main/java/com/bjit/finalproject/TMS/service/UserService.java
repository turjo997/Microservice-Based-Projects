package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.dto.ProfileDTO;
import com.bjit.finalproject.TMS.dto.UserRequestDTO;
import com.bjit.finalproject.TMS.dto.UserResponseDTO;
import com.bjit.finalproject.TMS.dto.authenticationDto.AuthenticationRequestDTO;
import com.bjit.finalproject.TMS.dto.authenticationDto.AuthenticationResponseDTO;
import com.bjit.finalproject.TMS.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
//    UserResponseDTO registerTrainer(UserRequestDTO userRequestDTO);
    AuthenticationResponseDTO login(AuthenticationRequestDTO requestModel);
    void registerAdmin(String email);
    List<UserResponseDTO> getAllTrainees(String roleName);
    List<UserResponseDTO> getAllTrainers(String roleName);
    List<UserResponseDTO> getAllUsers();
    ProfileDTO getUserProfile();
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    String loggedInUserEmail();
    String getUserRole();
}
