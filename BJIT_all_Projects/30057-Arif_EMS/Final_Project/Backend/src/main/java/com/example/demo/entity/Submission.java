package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "task_submission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Submission {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long submissionId;

    private String comment;
    private Date dateAndTime;
    private String answerFile;

    @ManyToOne
    @JoinColumn(name = "traineeId")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;
}
