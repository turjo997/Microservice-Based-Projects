package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity,Long> {

    ApplicantEntity findByEmail(String email);
}
