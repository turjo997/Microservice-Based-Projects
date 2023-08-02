package com.example.demo.repository;

import com.example.demo.entity.TaskEvaluation;
import com.example.demo.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEvaluateRepository extends JpaRepository<TaskEvaluation, Long> {

    List<TaskEvaluation> findByTrainee(Trainee trainee);

}
