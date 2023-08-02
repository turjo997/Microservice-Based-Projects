package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.Role;
import com.bjit.tss.entity.UserEntity;
import com.bjit.tss.model.AuthenticationResponse;
import com.bjit.tss.model.UserRequestModel;
import com.bjit.tss.repository.UserRepository;
import com.bjit.tss.service.UserService;
import com.bjit.tss.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<Object> register(UserRequestModel requestModel) {
        Role role_;
        String requestedRole = requestModel.getRole();

        if (Role.ADMIN.name().equals(requestedRole)) {
            role_ = Role.ADMIN;
        } else if (Role.APPLICANT.name().equals(requestedRole)) {
            role_ = Role.APPLICANT;
        } else if (Role.EVALUATOR.name().equals(requestedRole)) {
            role_ = Role.EVALUATOR;
        } else {
            role_ = Role.APPLICANT;
        }

        UserEntity userEntity = UserEntity.builder()
                .email(requestModel.getEmail())
                .name(requestModel.getName())
                .password(passwordEncoder.encode(requestModel.getPassword()))
                .role(role_)
                .build();

        userRepository.save(userEntity);
        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token(jwtService.generateToken(userEntity))
                .build();
        return new ResponseEntity<>(authRes, HttpStatus.CREATED);
    }

}