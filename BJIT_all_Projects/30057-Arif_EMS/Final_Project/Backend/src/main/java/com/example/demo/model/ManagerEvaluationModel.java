package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ManagerEvaluationModel {

    private Double attendance;
    private Double communicationSkill;
    private Double languageSkill;
    private Double qualityMindset;
    private Double sincerityAndHardWorking;
    private Double officeRules;
    private Double bjitToolsUsages;

}
