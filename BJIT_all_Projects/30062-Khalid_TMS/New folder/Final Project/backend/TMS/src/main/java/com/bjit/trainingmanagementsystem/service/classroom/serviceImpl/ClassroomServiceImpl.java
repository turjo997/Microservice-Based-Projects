package com.bjit.trainingmanagementsystem.service.classroom.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.classroom.ClassroomModel;
import com.bjit.trainingmanagementsystem.repository.classroom.ClassroomPostRepository;
import com.bjit.trainingmanagementsystem.repository.classroom.ClassroomRepository;
import com.bjit.trainingmanagementsystem.service.classroom.ClassroomService;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomPostRepository classroomPostRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClassroomModel createClassroom(Long batchId) {
        ClassroomEntity classroomEntity = ClassroomEntity.builder()
                .batchId(batchId)
                .build();

        ClassroomEntity savedClassroom = classroomRepository.save(classroomEntity);

        return modelMapper.map(savedClassroom, ClassroomModel.class);
    }

    @Override
    public ClassroomModel getClassroomByBatchId(Long batchId) {
        ClassroomEntity classroomEntity = classroomRepository.findByBatchId(batchId)
                .orElseThrow(() -> new NotFoundException("Classroom not found with Batch ID: " + batchId));

        return modelMapper.map(classroomEntity, ClassroomModel.class);
    }

}
