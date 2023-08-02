package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.Assignment;
import com.bjit.finalproject.TMS.model.AssignmentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentAnswerRepository extends JpaRepository<AssignmentAnswer, Long> {
    List<AssignmentAnswer> findByAssignment(Assignment assignment);
}
