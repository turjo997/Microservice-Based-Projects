package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "final_project_marks")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FinalProjectMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finalMarksId;

    private Long traineeId;
    private String criteria;
    private Double mark;

}
