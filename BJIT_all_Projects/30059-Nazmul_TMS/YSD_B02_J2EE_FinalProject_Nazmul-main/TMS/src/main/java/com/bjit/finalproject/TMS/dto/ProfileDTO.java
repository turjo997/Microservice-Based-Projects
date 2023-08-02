package com.bjit.finalproject.TMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {
    private Long id;
    private String email;
    private String fullName;
    private String bloodGroup;
    private String cgpa;
    private String passingYear;
    private String institute;
    private String nId;
    private String designation;
    private String experience;
    private String expertise;
    private String phoneNo;
    private String profilePicture;
    private MultipartFile imageFile;
}
