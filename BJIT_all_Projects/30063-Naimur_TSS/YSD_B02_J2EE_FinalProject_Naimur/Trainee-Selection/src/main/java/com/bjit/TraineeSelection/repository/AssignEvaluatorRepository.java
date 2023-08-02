package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.AssignEvaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignEvaluatorRepository extends JpaRepository<AssignEvaluator,Long> {
    boolean existsByEvaluatorIdAndCircularId(Long evaluatorId, Long circularId);

    AssignEvaluator findByEvaluatorId(Long evaluatorId);
}
