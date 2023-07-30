package com.example.UserService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestModel {
    private Long userId;
    private String name;
    private String email;
    private Long balance;
}
