package com.bjit.tss.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadMarkRequest {
    @Valid

    @NotNull(message = "Candidate ID cannot be null")
    private Long candidateId;

    @NotEmpty(message = "Marks list cannot be empty")
    private List<Float> marks;
}
