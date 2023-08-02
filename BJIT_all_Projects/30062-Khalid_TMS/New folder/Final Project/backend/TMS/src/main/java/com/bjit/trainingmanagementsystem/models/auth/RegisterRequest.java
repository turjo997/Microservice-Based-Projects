package com.bjit.trainingmanagementsystem.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private String role;
    private String profilePicturePath;
    private MultipartFile profilePictureMultipartFile;
    private String designation;
    private String joiningDate;
    private String yearsOfExperience;
    private String expertises;
    private String contactNumber;
    private String gender;
    private String dateOfBirth;
    private String degreeName;
    private String educationalInstitute;
    private String cgpa;
    private String passingYear;
    private String presentAddress;

    private  Long batchId;  //fk

}
