package com.bjit.finalproject.TMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String password;
    private String phoneNo;
    private MultipartFile profilePicture;
    private String role;

}
