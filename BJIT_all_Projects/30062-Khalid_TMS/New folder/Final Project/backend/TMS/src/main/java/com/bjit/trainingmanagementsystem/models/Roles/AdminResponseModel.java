package com.bjit.trainingmanagementsystem.models.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponseModel {

    private Long adminId;
    private String fullName;
    private String profilePicturePath;
    private String contactNumber;
    private String email;
    private String gender;

    private Long userId;
}
