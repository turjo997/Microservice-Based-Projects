package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateAdmin {

    private String designation;
    private Long yearOfExperience;
    private String email;
    private String password;
    private String fullName;
    private String address;

}
