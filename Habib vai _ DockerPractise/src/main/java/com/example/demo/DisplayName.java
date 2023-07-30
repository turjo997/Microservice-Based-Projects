package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisplayName {
    @GetMapping("/habib")
    public String name () {
         return "Hello! I am Habibullah Howlader!";
    }
}
