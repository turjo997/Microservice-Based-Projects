package com.bjit.trainingmanagementsystem.entities.classroomEntites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class ClassroomPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String textData;
    private String filePath;
    private String postDate; //date and time

    @OneToMany(cascade = CascadeType.ALL)
    private List<ClassroomCommentEntity> comments = new ArrayList<>();

    private String trainerName;
    private Long trainerId;  //fk
    private Long classroomId; //fk
}
