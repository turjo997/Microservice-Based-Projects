package com.bjit.trainingmanagementsystem.entities.classroomEntites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    private String textData;
    private String postDate; //date and time

    private Long noticeBoardId;
    private Long trainerId;
    private String trainerName;
    private String noticeTitle;
}

