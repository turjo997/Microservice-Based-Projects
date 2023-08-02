package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginResponseModel {
    private String token;
    private String name;
    private String role;
    private Long userId;
}
