package com.example.demo.repository;

import com.example.demo.entity.FinalProjectCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FinalProjectCriteriaRepository extends JpaRepository<FinalProjectCriteria , Long> {

    Set<FinalProjectCriteria> findByBatchId(Long batchId);

}
