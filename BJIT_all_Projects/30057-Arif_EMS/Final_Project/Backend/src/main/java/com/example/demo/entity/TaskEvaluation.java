package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskEvaluation {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long evaluationId;

    private String type;
    private Double codingEquipment;
    private Double codeOutput;
    private Double codeQuality;
    private Double codeDemonstration;
    private Double codeUnderstanding;
    private Double totalMarks;

    @ManyToOne
    @JoinColumn(name = "traineeId")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;
}
