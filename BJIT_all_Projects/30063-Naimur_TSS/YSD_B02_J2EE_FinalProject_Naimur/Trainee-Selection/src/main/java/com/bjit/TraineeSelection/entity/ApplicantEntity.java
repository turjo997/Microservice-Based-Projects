package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "applicant_reg")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String contactNumber;
    private String degreeName;
    private String educationalInstitute;
    private String cgpa;
    private String passingYear;
    private String address;
    private Long userId;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Lob
    @Column(name = "cv", columnDefinition = "LONGBLOB")
    private byte[] cv;





}
