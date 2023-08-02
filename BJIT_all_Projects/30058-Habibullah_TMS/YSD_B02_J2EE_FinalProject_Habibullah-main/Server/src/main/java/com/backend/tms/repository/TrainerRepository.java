package com.backend.tms.repository;

import com.backend.tms.entity.BatchEntity;
import com.backend.tms.entity.CourseEntity;
import com.backend.tms.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
    TrainerEntity findByEmail (String email);
    boolean existsById(Long id);
    TrainerEntity findByCoursesName(String courseName);
    TrainerEntity findByCoursesContains(CourseEntity course);



}
