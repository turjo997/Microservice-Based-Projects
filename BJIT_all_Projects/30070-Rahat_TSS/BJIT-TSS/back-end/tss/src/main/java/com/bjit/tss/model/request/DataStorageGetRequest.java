package com.bjit.tss.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataStorageGetRequest {
    @Valid

    @NotEmpty(message = "Data Key is required")
    private String dataKey;
}