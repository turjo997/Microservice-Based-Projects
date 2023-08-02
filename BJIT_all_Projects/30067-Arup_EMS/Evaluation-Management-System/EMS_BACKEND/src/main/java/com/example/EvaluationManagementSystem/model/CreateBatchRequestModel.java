package com.example.EvaluationManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBatchRequestModel {


    private String batchName;
    private Date startingDate;
    private Date endingDate;
    private String description;
    private Long totalTrainee;
    private List<Long> traineeIds;
}
