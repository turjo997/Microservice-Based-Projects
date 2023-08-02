package com.bjit.trainingmanagementsystem.repository.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomCommentRepository extends JpaRepository<ClassroomCommentEntity, Long> {
    List<ClassroomCommentEntity> findByPostId(Long postId);
}
