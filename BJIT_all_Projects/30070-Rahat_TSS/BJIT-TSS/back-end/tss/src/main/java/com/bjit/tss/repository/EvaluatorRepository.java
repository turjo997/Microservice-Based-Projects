package com.bjit.tss.repository;

import com.bjit.tss.entity.EvaluatorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluatorRepository extends JpaRepository<EvaluatorInfo, Long> {

}