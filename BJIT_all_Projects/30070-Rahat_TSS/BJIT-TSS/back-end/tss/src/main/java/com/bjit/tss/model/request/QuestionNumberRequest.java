package com.bjit.tss.model.request;

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
public class QuestionNumberRequest {

    @Valid

    @NotEmpty(message = "Question type cannot be null or empty")
    private String questionType;

    @NotNull(message = "Question numbers cannot be null")
    private Long questionNumbers;
}