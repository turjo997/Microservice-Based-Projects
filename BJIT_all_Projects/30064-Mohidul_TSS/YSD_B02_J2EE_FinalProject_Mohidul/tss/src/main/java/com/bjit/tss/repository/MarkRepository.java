package com.bjit.tss.repository;

import com.bjit.tss.entity.ExamEntity;
import com.bjit.tss.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Long> {
    List<MarkEntity> findByApplicantId(Long applicantId);

    @Query("SELECT m FROM MarkEntity m WHERE m.circular = :circular")
    List<MarkEntity> findByCircular(@Param("circular") String circular);

    @Query("SELECT m FROM MarkEntity m WHERE m.exam = :examEntity")
    Optional<MarkEntity> findByExam(@Param("examEntity") ExamEntity examEntity);

    Optional<MarkEntity> findByExamAndApplicantId(ExamEntity examEntity, Long applicantId);

}
