package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "totalMarks" )

public class TotalMarks {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long finalMarksId;

    private Double dailyTask;
    private Double miniProject;
    private Double midTerm;
    private Double finalProject;
    private Double managerEvaluation;
    private Double hrEvaluation;
    private Double totalMarks;

    @OneToOne
    @JoinColumn(name = "traineeId")
    private Trainee trainee;

}
