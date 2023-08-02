package com.bjit.trainingmanagementsystem.entities;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.AssignmentEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "batch")
public class BatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;
    private String batchName;
    private String startDate;
    private String endDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "batch_trainer",
            joinColumns = @JoinColumn(name = "batch_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private List<TrainerEntity> trainers = new ArrayList<>();

}
