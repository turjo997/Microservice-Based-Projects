package com.bjit.trainingmanagementsystem.models.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponseModel {

    private Long submissionId;
    private String submissionTime;
    private String filePath;
    private Long assignmentId;
    private String turnedIn;
    private String assignmentTitle;
    private String traineeName;
    private Long traineeId;
}
