package com.bjit.trainingmanagementsystem.repository.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByNoticeBoardId(Long noticeBoardId);
}
