package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.ApplicantEntity;
import com.bjit.TraineeSelection.entity.AssignEvaluator;
import com.bjit.TraineeSelection.entity.EvaluatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluatorRepository extends JpaRepository<EvaluatorEntity,Long> {
    EvaluatorEntity findByEmail(String email);

}
