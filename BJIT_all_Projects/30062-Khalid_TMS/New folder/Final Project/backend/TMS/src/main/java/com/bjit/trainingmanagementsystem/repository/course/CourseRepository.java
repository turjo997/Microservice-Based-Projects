package com.bjit.trainingmanagementsystem.repository.course;

import com.bjit.trainingmanagementsystem.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    List<CourseEntity> findByBatchId(Long batchId);
}
