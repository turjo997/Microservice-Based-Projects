package com.bjit.trainingmanagementsystem.repository.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    Optional<ClassroomEntity> findByBatchId(Long batchId);
}
