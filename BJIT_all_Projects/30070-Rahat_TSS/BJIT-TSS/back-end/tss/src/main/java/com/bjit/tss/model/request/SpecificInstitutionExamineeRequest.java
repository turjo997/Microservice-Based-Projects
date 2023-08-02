package com.bjit.tss.model.request;

import com.bjit.tss.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecificInstitutionExamineeRequest {

    @Valid

    @NotNull(message = "Role is required")
    private Role role;

    @NotEmpty(message = "Batch Code is required")
    private String batchCode;

    @NotEmpty(message = "Educational Institution is required")
    private String educationalInstitution;
}
