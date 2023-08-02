package com.bjit.finalproject.TMS.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentRequestDTO {
    private String assignmentTitle;
    private MultipartFile assignmentFile;
    private String endTime;
    private Long scheduleId;
}
