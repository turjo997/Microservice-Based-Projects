package com.example.productapp.config.controller;

import com.example.productapp.config.FeignClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
public class HelloController {
    Logger logger = Logger.getLogger("HelloController");

    @Autowired
    FeignClientConfig feignClientConfig;
    @GetMapping("/hello")
    public String hello (){
        logger.info("Hello");
        return "Hello Controller  "+feignClientConfig.getCount();
    }
}