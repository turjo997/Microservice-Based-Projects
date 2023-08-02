package com.bjit.trainingmanagementsystem.repository.assignment;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
    List<AssignmentEntity> findByBatchId(Long batchId);
}
