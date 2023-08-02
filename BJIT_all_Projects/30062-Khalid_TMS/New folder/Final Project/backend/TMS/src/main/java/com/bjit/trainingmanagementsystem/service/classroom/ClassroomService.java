package com.bjit.trainingmanagementsystem.service.classroom;

import com.bjit.trainingmanagementsystem.models.classroom.ClassroomModel;

public interface ClassroomService {
    ClassroomModel createClassroom(Long batchId);

    ClassroomModel getClassroomByBatchId(Long batchId);
}
