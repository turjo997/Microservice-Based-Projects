package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FinalMarksWeight {
    private Double dailyTask;
    private Double miniProject;
    private Double midTerm;
    private Double finalProject;
    private Double managerEvaluation;
    private Double hrEvaluation;
}