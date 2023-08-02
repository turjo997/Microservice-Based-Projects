package com.bjit.tss.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluatorRegisterRequest {

    @Valid

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Course Name is required")
    private String name;

    @NotEmpty(message = "Course Name is required")
    private String password;
}