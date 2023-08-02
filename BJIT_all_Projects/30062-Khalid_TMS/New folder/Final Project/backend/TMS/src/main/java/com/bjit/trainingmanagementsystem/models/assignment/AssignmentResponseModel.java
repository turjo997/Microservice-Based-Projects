package com.bjit.trainingmanagementsystem.models.assignment;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.SubmissionEntity;
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
public class AssignmentResponseModel {

    private Long assignmentId;
    private String title;
    private String description;
    private String deadline;
    private String filePath;

    private List<SubmissionEntity> submissions = new ArrayList<>();

    private Long trainerId;
    private Long batchId;
}
