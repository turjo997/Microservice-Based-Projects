package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task")

public class Task {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long taskId;

    private String taskTitle;
    private String taskType;
    private Date taskDate;
    private String questionFile;

    @ManyToOne
    @JoinColumn(name = "batchId")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "trainerId")
    private Trainer trainer;

}
