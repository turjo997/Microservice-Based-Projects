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
public class TraineeUpdateModel {

    private String fullName;
    private String profilePicture;
    private String gender;
    private String dateOfBirth;
    private String email;
    private String contactNumber;
    private String degreeName;
    private String educationalInstitute;
    private String cgpa;
    private String passingYear;
    private String presentAddress;

    private Long batchId;
}
