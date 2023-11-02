package com.bjitacademy.securityModule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestModel {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    private List<String> roles;
}
