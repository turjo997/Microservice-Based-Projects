package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.classroom.ClassRoomAttachment;
import com.bjit.finalproject.TMS.model.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomAttachmentRepository extends JpaRepository<ClassRoomAttachment, Long> {
    List<ClassRoomAttachment> findByClassroom(Classroom classroom);
}
