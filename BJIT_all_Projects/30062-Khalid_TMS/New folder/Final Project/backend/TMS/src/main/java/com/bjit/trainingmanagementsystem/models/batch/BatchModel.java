package com.bjit.trainingmanagementsystem.models.batch;

import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchModel {

    private Long batchId;
    private String batchName;
    private String startDate;
    private String endDate;
    private List<TrainerEntity> trainers = new ArrayList<>();
}
