package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.ApplicantEntity;
import com.bjit.TraineeSelection.entity.Application;
import com.bjit.TraineeSelection.entity.Circular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByCircular(Optional<Circular> circular);

    Optional<Application> findByApplicantEntityAndApprovalStatus(ApplicantEntity applicantEntity, boolean b);

    boolean existsByApplicantEntityApplicantIdAndCircularCircularId(Long newapplicantId, Long newcircularId);

    List<Application> findByApprovalStatus(boolean b);

    boolean existsByApplicantEntity_ApplicantIdAndApprovalStatusTrue(Long applicantId);

    List<Application> findByApplicantEntity_ApplicantId(Long applicantId);
}
