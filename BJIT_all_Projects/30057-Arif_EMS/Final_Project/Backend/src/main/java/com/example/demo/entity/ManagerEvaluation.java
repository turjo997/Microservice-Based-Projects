package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "managerEvaluation" )

public class ManagerEvaluation {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long managerEvaluationId;

    private Double attendance;
    private Double communicationSkill;
    private Double languageSkill;
    private Double qualityMindset;
    private Double sincerityAndHardWorking;
    private Double officeRules;
    private Double bjitToolsUsages;
    private Double totalMarks;

    @OneToOne
    @JoinColumn(name = "traineeId")
    private Trainee trainee;

}
