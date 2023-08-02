package com.bjit.TraineeSelection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorPrimaryDto {

    private Long evaluatorId;
    private String firstName;
    private String lastName;
    private String specialization;

}
