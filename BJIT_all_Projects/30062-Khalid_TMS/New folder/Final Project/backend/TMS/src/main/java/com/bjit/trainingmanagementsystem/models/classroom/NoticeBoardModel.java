package com.bjit.trainingmanagementsystem.models.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeBoardModel {

    private Long noticeBoardId;
    private List<NoticeEntity> notices = new ArrayList<>();
    private Long batchId;
}
