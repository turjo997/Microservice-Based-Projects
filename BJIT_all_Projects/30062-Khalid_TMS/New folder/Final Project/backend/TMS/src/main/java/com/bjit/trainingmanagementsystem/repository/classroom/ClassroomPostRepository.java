package com.bjit.trainingmanagementsystem.repository.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomPostRepository extends JpaRepository<ClassroomPostEntity, Long> {

    List<ClassroomPostEntity> findByClassroomId(Long classroomId);
}
