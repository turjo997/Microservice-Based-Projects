package com.bjit.traineeSelectionSystem.TSS.model.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Valid

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
}