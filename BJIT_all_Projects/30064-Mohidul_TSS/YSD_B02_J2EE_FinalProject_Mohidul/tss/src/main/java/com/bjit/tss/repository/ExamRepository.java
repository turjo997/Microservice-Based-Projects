package com.bjit.tss.repository;

import com.bjit.tss.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
    ExamEntity findByExamCode(Long examCode);
}
