package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.AdminEntity;
import com.bjit.TraineeSelection.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
}
