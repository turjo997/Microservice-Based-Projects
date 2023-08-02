package com.bjit.tss.repository;

import com.bjit.tss.entity.AdmitcardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmitcardRepository extends JpaRepository<AdmitcardEntity, Long> {
    @Query("SELECT a FROM AdmitcardEntity a WHERE a.candidateId.applicant.applicantId = :applicantId")
    Optional<AdmitcardEntity> findByApplicantId(@Param("applicantId") Long applicantId);

}
