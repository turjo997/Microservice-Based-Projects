package com.suffixIT.springsecurity.service;

import com.suffixIT.springsecurity.model.AuthenticationResponse;
import com.suffixIT.springsecurity.model.SignInRequest;
import com.suffixIT.springsecurity.model.SignUpRequestModel;

public interface AuthenticationService {

    AuthenticationResponse signup(SignUpRequestModel request);

    AuthenticationResponse signin(SignInRequest request);
}
