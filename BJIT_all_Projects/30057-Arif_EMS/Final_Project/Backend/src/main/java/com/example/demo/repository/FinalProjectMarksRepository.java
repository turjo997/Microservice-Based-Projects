package com.example.demo.repository;

import com.example.demo.entity.FinalProjectMarks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FinalProjectMarksRepository extends JpaRepository<FinalProjectMarks , Long> {

    public Set<FinalProjectMarks> findByTraineeId(Long traineeId);
}
