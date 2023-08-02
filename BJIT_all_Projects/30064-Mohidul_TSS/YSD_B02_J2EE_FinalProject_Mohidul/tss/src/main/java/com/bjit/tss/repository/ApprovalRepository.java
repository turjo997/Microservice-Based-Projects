package com.bjit.tss.repository;

import com.bjit.tss.entity.ApplicantEntity;
import com.bjit.tss.entity.ApprovalEntity;
import com.bjit.tss.entity.CircularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {

    List<ApprovalEntity> findByApplicant_ApplicantIdAndCircularNot(Long applicantId, CircularEntity circular);

    List<ApprovalEntity> findByIsApprovedTrue();

    Optional<ApprovalEntity> findByApplicantAndCircular(ApplicantEntity applicant, CircularEntity circular);

    Optional<ApprovalEntity> findByApplicant_ApplicantIdAndCircular_CircularId(Long applicantId, Long circularId);
}
