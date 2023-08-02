package com.bjit.finalproject.TMS.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentAnswerResponse {
    private Long answerId;
    private String answerFile;
    private String submittedBy;
    private String assignmentTitle;
}
