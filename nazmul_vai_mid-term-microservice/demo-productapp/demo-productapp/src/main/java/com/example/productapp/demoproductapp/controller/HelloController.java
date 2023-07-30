package com.example.productapp.demoproductapp.controller;

import com.example.productapp.demoproductapp.config.FeignClientConfig;
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
    public String hello(){
        logger.info("HI");
        return "Hello there: "+ "COUNT is: " +feignClientConfig.getCount();

    }
}
