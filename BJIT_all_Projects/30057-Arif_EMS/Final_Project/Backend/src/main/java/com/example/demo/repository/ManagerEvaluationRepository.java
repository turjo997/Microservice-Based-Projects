package com.example.demo.repository;

import com.example.demo.entity.ManagerEvaluation;
import com.example.demo.entity.TaskEvaluation;
import com.example.demo.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerEvaluationRepository extends JpaRepository<ManagerEvaluation , Long> {

    ManagerEvaluation findByTrainee(Trainee trainee);

}
