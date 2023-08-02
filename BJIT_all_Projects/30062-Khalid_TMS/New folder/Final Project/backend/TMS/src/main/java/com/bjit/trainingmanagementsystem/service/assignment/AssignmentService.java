package com.bjit.trainingmanagementsystem.service.assignment;

import com.bjit.trainingmanagementsystem.models.assignment.AssignmentCreateRequest;
import com.bjit.trainingmanagementsystem.models.assignment.AssignmentResponseModel;
import com.bjit.trainingmanagementsystem.models.assignment.AssignmentUpdateModel;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;


public interface AssignmentService {
    AssignmentResponseModel create(AssignmentCreateRequest assignmentCreateRequest);
    AssignmentResponseModel findAssignmentById(Long assignmentId);
    List<AssignmentResponseModel> findAssignmentsByBatchId(Long batchId);
    AssignmentResponseModel updateAssignment(AssignmentUpdateModel assignmentUpdateModel, Long assignmentId);

    byte[] getAssignmentFileData(Long assignmentId) throws IOException;

    Resource downloadAssignmentById(Long assignmentId);

    Resource getAssignmentFileResource(Long assignmentId);
}
