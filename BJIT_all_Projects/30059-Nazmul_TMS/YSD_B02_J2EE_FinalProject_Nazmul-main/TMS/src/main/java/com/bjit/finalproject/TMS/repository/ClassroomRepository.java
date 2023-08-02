package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByClassroomTitle(String classroomTitle);
    Optional<Classroom> findByBatch(Batch batch);
}
