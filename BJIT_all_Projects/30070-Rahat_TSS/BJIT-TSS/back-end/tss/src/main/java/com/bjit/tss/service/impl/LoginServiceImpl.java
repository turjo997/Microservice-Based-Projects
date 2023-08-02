package com.bjit.tss.service.impl;

import com.bjit.tss.config.JwtService;
import com.bjit.tss.entity.EvaluatorInfo;
import com.bjit.tss.entity.LoginInfo;
import com.bjit.tss.entity.UserInfo;
import com.bjit.tss.enums.Role;
import com.bjit.tss.exception.AuthenticationException;
import com.bjit.tss.exception.UserException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.response.AuthenticationResponse;
import com.bjit.tss.model.request.LoginRequest;
import com.bjit.tss.model.response.ValidationResponse;
import com.bjit.tss.repository.EvaluatorRepository;
import com.bjit.tss.repository.LoginRepository;
import com.bjit.tss.repository.UserRepository;
import com.bjit.tss.service.LoginService;
import com.bjit.tss.model.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EvaluatorRepository evaluatorRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    ));
        } catch (Exception ex) {
            throw new AuthenticationException("Invalid Email and Password");
        }

        LoginInfo loginInfo = loginRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(loginInfo);
        AuthenticationResponse authenticationResponse = null;
        switch (loginInfo.getRole()) {
            case ADMIN -> {authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .role(loginInfo.getRole())
                    .build();
                System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+" logged in to the system successfully.");
            }
            case EVALUATOR -> {authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .role(loginInfo.getRole())
                    .evaluatorInfo(loginInfo.getEvaluatorInfo())
                    .build();
                System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+ " logged in successfully." );
            }
            default -> {authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .role(loginInfo.getRole())
                    .userInfo(loginInfo.getUserInfo())
                    .build();
            System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+ " logged in successfully." );}
        }

        return ApiResponseMapper.mapToResponseEntityOK(authenticationResponse,"Login successfully done.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> validation() {

        LoginInfo loginInfo= (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (loginInfo.getRole()== Role.ADMIN){
            ValidationResponse<?> validationResponse = ValidationResponse.builder()
                    .data(null)
                    .role(loginInfo.getRole())
                    .build();
            System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+" automatically logged in to the system successfully.");
            return ApiResponseMapper.mapToResponseEntityOK(validationResponse, "Valid User");
        }
        if (loginInfo.getRole()== Role.EVALUATOR){
            Optional<EvaluatorInfo> evaluatorInfo = evaluatorRepository.findById(loginInfo.getEvaluatorInfo().getEvaluatorId());
            if (evaluatorInfo.isEmpty()){
                throw new UserException("Evaluator not exists");
            }
            ValidationResponse<?> validationResponse = ValidationResponse.builder()
                    .data(evaluatorInfo)
                    .role(loginInfo.getRole())
                    .build();
            System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+" automatically logged in to the system successfully.");
            return ApiResponseMapper.mapToResponseEntityOK(validationResponse, "Valid User");
        }

        if (loginInfo.getRole()== Role.APPLICANT){
            Optional<UserInfo> userInfo = userRepository.findById(loginInfo.getUserInfo().getUserId());
            if (userInfo.isEmpty()){
                throw new UserException("User not exists");
            }
            ValidationResponse<?> validationResponse = ValidationResponse.builder()
                    .data(userInfo)
                    .role(loginInfo.getRole())
                    .build();
            System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+" automatically logged in to the system successfully.");
            return ApiResponseMapper.mapToResponseEntityOK(validationResponse, "Valid User");
        }
        if (loginInfo.getRole()== Role.USER){
            Optional<UserInfo> userInfo2 = userRepository.findById(loginInfo.getUserInfo().getUserId());
            if (userInfo2.isEmpty()){
                throw new UserException("User not exists");
            }
            ValidationResponse<?> validationResponse = ValidationResponse.builder()
                    .data(userInfo2)
                    .role(loginInfo.getRole())
                    .build();
            System.out.println(loginInfo.getRole()+" with the email "+loginInfo.getEmail()+" automatically logged in to the system successfully.");
            return ApiResponseMapper.mapToResponseEntityOK(validationResponse, "Valid User");

        }
        throw new UserException("Invalid user");
    }
}