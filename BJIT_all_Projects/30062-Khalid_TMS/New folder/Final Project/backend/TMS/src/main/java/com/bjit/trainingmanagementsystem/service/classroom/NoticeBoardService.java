package com.bjit.trainingmanagementsystem.service.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeBoardEntity;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeBoardModel;


public interface NoticeBoardService {

    NoticeBoardEntity createNoticeBoard(Long batchId);
    NoticeBoardModel getNoticeBatchId(Long batchId);
}
