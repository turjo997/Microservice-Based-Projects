package com.suffixIT.springsecurityclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello Welcome to Daily Code Buffer!!";
    }
}
