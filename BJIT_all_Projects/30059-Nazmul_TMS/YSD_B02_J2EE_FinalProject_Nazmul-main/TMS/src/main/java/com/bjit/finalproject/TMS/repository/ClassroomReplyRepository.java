package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.classroom.ClassRoomAttachment;
import com.bjit.finalproject.TMS.model.classroom.ClassroomReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomReplyRepository extends JpaRepository<ClassroomReply, Long> {
    List<ClassroomReply> findByClassRoomAttachment(ClassRoomAttachment classRoomAttachment);
}
