package com.bjit.trainingmanagementsystem.models.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequest {

    private String textData;
    private MultipartFile postFile; //files
    private String postDate; //date and time

    private Long trainerId;  //fk
    private Long classroomId; //fk
}
