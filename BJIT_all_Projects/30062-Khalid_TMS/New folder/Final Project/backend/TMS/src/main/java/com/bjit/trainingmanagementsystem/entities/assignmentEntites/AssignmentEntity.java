package com.bjit.trainingmanagementsystem.entities.assignmentEntites;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "assignment")
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;
    private String title;
    private String description;
    private String deadline;
    private String filePath;

    @OneToMany
    private List<SubmissionEntity> submissions = new ArrayList<>();

    private Long trainerId;     //fk
    private Long batchId;       //fk
}
