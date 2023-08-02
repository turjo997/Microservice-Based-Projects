package com.bjit.tss.repository;

import com.bjit.tss.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
    List<ResultEntity> findByApplicantId(Long applicantId);
    List<ResultEntity> findByCircularTitle(String circularTitle);
}

