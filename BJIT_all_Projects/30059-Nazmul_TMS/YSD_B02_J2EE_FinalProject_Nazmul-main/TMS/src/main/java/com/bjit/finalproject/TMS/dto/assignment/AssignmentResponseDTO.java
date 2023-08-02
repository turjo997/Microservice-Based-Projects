package com.bjit.finalproject.TMS.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponseDTO {
    @Id
    private Long id;
    private String assignmentTitle;
    private String endTime;
    private String message;
//    private String scheduleName;
    private String fileName;
}
