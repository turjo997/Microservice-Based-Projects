package com.bjit.trainingmanagementsystem.repository.assignment;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Long> {

    List<SubmissionEntity> findByAssignmentId(Long assignmentId);

    List<SubmissionEntity> findByAssignmentIdAndTraineeId(Long assignmentId, Long traineeId);
}
