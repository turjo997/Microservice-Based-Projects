package com.bjit.tss.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="applicants")
public class ApplicantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
