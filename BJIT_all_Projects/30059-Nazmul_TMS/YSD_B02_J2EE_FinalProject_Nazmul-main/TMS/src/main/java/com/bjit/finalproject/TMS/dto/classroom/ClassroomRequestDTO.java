package com.bjit.finalproject.TMS.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomRequestDTO {
    private String message;
    private MultipartFile fileName;
    private String trainerEmail;
    private Long classroomId;
}
