package com.bjit.trainingmanagementsystem.models.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionCreateRequest {

    private String submissionTime;
    private MultipartFile submissionMultipartFile;
    private Long assignmentId;
    private Long traineeId;
}
