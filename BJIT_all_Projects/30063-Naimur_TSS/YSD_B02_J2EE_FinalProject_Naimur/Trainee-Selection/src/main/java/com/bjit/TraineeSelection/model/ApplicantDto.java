package com.bjit.TraineeSelection.model;

import com.bjit.TraineeSelection.entity.Role;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantDto {
    private Long applicantId;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String password;
    private String contactNumber;
    private String degreeName;
    private String educationalInstitute;
    private String cgpa;
    private String passingYear;
    private String address;
    private Role role;

}
