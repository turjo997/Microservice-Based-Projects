package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.Assignment;
import com.bjit.finalproject.TMS.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByAssignmentTitle(String assignmentTitle);
    List<Assignment> findBySchedule(Schedule schedule);
}
