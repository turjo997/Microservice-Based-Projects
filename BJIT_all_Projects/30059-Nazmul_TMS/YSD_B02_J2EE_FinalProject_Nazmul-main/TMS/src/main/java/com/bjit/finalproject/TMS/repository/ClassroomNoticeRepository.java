package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.classroom.Classroom;
import com.bjit.finalproject.TMS.model.classroom.ClassroomNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomNoticeRepository extends JpaRepository<ClassroomNotice, Long> {
    Optional<ClassroomNotice> findByClassroom(Classroom classroom);
    List<ClassroomNotice> findAllByClassroom(Classroom classroom);
}
