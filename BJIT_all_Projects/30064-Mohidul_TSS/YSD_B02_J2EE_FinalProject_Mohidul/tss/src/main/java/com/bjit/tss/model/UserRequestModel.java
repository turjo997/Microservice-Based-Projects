package com.bjit.tss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestModel {
    @NotNull
    private String name;
    @Email(message = "The provided email address is invalid.")
    private String email;
    @NotEmpty
    private String password;
    private String role;
}
