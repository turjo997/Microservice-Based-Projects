package com.bjit.TraineeSelection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignEvaluatorDto {

    private Long assignId;
    private Long evaluatorId;
    private Long circularId;
}
