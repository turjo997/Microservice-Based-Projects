package com.bjit.tss.repository;

import com.bjit.tss.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
    Optional<ResourceEntity> findByApplicant_ApplicantId(Long applicantId);
}
