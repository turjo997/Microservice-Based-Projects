package com.bjit.tss.repository;

import com.bjit.tss.entity.WrittenTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WrittenTestRepository extends JpaRepository<WrittenTestEntity, Long> {
    WrittenTestEntity findByHiddenCode(Long hiddenCode);
    Optional<WrittenTestEntity> findByApplicantId(Long applicantId);

}
