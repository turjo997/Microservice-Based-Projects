package com.bjit.trainingmanagementsystem.service.assignment;

import com.bjit.trainingmanagementsystem.models.assignment.SubmissionCreateRequest;
import com.bjit.trainingmanagementsystem.models.assignment.SubmissionResponseModel;
import org.springframework.core.io.Resource;

import java.util.List;

public interface SubmissionService {
    SubmissionResponseModel createSubmission(SubmissionCreateRequest submissionCreateRequest);
    SubmissionResponseModel getSubmissionById(Long submissionId);
    List<SubmissionResponseModel> getSubmissionsByAssignmentId(Long assignmentId);

    SubmissionResponseModel getTurnedInStatus(Long assignmentId, Long traineeId);
    Resource downloadSubmissionById(Long submissionId);
}
