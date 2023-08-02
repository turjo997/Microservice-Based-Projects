package com.example.demo.repository;

import com.example.demo.entity.HREvaluation;
import com.example.demo.entity.TaskEvaluation;
import com.example.demo.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HREvaluationRepository extends JpaRepository<HREvaluation , Long> {

    public HREvaluation findByTrainee(Trainee trainee);

}
