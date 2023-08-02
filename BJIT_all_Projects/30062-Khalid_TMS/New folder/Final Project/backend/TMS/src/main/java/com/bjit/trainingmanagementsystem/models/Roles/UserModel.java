package com.bjit.trainingmanagementsystem.models.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private Long userId;
    private String email;
    private String roleName;
}
