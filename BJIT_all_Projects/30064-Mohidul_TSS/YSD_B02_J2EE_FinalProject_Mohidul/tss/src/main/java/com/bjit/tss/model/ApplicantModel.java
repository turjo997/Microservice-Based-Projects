package com.bjit.tss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantModel {
    private Long applicantId;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private String email;
    private String contactNumber;
    private String degreeName;
    private String educationalInstitute;
    private Double cgpa;
    private Integer passingYear;
    private String presentAddress;
}
