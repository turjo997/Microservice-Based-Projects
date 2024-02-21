package com.ullash.springaop;

import org.springframework.stereotype.Component;

@Component
public class User {

    public void getUser(String username) {
        throw new UserNotFoundException("User not found: " + username);
    }
}
