package com.backend.tms.model.Classroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostReqModel {
    private Long id;
    private String title;
    private Long classroomId;
    private Long trainerId;
    private MultipartFile file;
}
