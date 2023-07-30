package com.HabibDev.BookShopApplication.service.imp;
import com.HabibDev.BookShopApplication.entity.UserEntity;
import com.HabibDev.BookShopApplication.exception.custom.AuthenticationException;
import com.HabibDev.BookShopApplication.exception.custom.RegistrationException;
import com.HabibDev.BookShopApplication.exception.custom.UserAlreadyExistsException;
import com.HabibDev.BookShopApplication.model.AuthenticationRequest;
import com.HabibDev.BookShopApplication.model.AuthenticationResponse;
import com.HabibDev.BookShopApplication.model.UserRequestModel;
import com.HabibDev.BookShopApplication.repository.UserRepository;
import com.HabibDev.BookShopApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserService {

    private final UserRepository userRepository;

    //register service Implementation
    @Override
    public ResponseEntity<Object> register(UserRequestModel requestModel) {
        String email = requestModel.getEmail();

        // Check if a user with the given email already exists
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        //checking if all fields are valid
        if (requestModel == null ||
                requestModel.getFirstName() == null ||
                requestModel.getLastName() == null ||
                requestModel.getEmail() == null ||
                requestModel.getAddress() == null ||
                requestModel.getPassword() == null) {
            throw new RegistrationException("Missing required fields");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .firstName(requestModel.getFirstName())
                .lastName(requestModel.getLastName())
                .address(requestModel.getAddress())
                .password(requestModel.getPassword())
                .build();

        userRepository.save(userEntity);


        return new ResponseEntity<>("Your registration is Successfully done!", HttpStatus.CREATED);
    }



    //login service Implementation
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) throws AuthenticationException {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        // Perform your custom logic to verify the username and password
        // For example, you can query the UserRepository to check if the provided email and password match a user record
        UserEntity user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect email or password");
        }

        // Populate the AuthenticationResponse with user information
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .build();

        return ResponseEntity.ok(authenticationResponse);
    }


}

