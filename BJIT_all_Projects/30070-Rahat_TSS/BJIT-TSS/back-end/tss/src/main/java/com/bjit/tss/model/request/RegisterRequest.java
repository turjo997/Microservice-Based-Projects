package com.bjit.tss.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Valid

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    @NotNull(message = "Date of birth cannot be null")
    private Date dateOfBirth;

    @NotEmpty(message = "Contact number cannot be empty")
    private String contactNumber;

    @NotEmpty(message = "Degree name cannot be empty")
    private String degreeName;

    @NotEmpty(message = "Educational institute cannot be empty")
    private String educationalInstitute;

    @NotNull(message = "CGPA cannot be null")
    private Float cgpa;

    @NotNull(message = "Passing year cannot be null")
    private Integer passingYear;

    @NotEmpty(message = "Present address cannot be empty")
    private String presentAddress;

}
