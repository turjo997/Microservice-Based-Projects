package com.bjit.trainingmanagementsystem.models.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomCommentEntity;
import jakarta.persistence.CascadeType;
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
public class PostModel {

    private Long postId;
    private String textData;
    private String filePath;
    private String postDate;

    private List<ClassroomCommentEntity> comments = new ArrayList<>();

    private String trainerName;
    private Long trainerId;  //fk
    private Long classroomId; //fk
}
