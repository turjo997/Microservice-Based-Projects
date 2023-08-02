package com.bjit.trainingmanagementsystem.repository.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Long> {
    Optional<NoticeBoardEntity> findByBatchId(Long batchId);
}
