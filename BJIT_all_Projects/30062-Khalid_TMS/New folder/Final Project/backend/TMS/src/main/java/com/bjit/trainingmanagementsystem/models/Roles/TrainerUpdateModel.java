package com.bjit.trainingmanagementsystem.models.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerUpdateModel {

    private String fullName;
    private String profilePicture;
    private String designation;
    private String joiningDate;
    private String yearsOfExperience;
    private String expertises;
    private String contactNumber;
    private String email;
    private String presentAddress;

}
