package com.Habib.productApp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HelloController {
    Logger logger = Logger.getLogger("HelloController");

    @Autowired
    FeignClientsConfig feignClientsConfig;

    @GetMapping("/hello")
    public String hello (){
        logger.info("Hello");
        return "Hello service" + feignClientsConfig.getCount();
    }
}
