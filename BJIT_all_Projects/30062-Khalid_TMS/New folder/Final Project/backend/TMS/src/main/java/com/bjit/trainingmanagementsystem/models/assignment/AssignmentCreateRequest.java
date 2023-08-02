package com.bjit.trainingmanagementsystem.models.assignment;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.SubmissionEntity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentCreateRequest {

    private String title;
    private String description;
    private String deadline;
    private MultipartFile assignmentMultipartFile;

    private Long trainerId;     //fk
    private Long batchId;       //fk
}
