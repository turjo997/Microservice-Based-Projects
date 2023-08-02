package com.bjit.tss.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignAnswerSheetRequest {

    @Valid

    @NotNull(message = "Evaluator ID is required")
    private Long evaluatorId;

    @NotNull(message = "Candidate IDs are required")
    private List<Long> candidateIds;
}