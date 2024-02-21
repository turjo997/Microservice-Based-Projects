package com.ullash.springaop;

import org.springframework.stereotype.Component;

@Component
public class CustomUserService {

    public CustomUser getUser(String username) {
        return new CustomUser(username , "User@gmail.com");
    }
}
