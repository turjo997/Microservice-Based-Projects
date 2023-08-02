package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskEvaluationModel {

    private Double codingEquipment;
    private Double codeOutput;
    private Double codeQuality;
    private Double codeDemonstration;
    private Double codeUnderstanding;

}
