package com.suffixIT.springsecurity.service.impl;

import com.suffixIT.springsecurity.entity.Role;
import com.suffixIT.springsecurity.entity.UserEntity;
import com.suffixIT.springsecurity.model.SignInRequest;
import com.suffixIT.springsecurity.model.AuthenticationResponse;
import com.suffixIT.springsecurity.model.SignUpRequestModel;
import com.suffixIT.springsecurity.repository.UserRepository;
import com.suffixIT.springsecurity.service.AuthenticationService;
import com.suffixIT.springsecurity.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse signup(SignUpRequestModel request) {
        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Objects.equals(request.getRole(),"ADMIN")? Role.ADMIN : Role.USER)
                .build();
        userRepository.save(userEntity);
        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token(jwtService.generateToken(userEntity))
                .build();
        return new ResponseEntity<>(authRes, HttpStatus.CREATED).getBody();
    }

    @Override
    public AuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
