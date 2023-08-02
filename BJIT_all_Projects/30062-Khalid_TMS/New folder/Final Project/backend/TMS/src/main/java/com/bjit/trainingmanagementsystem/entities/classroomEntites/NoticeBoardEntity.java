package com.bjit.trainingmanagementsystem.entities.classroomEntites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notice_board")
public class NoticeBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeBoardId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<NoticeEntity> notices = new ArrayList<>();

    private Long batchId;    //fk
}