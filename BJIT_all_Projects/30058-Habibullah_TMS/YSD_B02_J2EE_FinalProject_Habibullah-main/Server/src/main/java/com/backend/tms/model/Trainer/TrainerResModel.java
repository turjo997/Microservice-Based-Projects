package com.backend.tms.model.Trainer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerResModel {
    private String fullName;
    private String email;
    private String profilePicture;
    private String designation;
    private String joiningDate;
    private int yearsOfExperience;
    private String expertises;
    private String contactNumber;
    private String presentAddress;
}
