package com.bjit.trainingmanagementsystem.repository.role;

import com.bjit.trainingmanagementsystem.entities.roleEntites.TraineeEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<TraineeEntity, Long> {
    List<TraineeEntity> findByBatchIdIsNull();

    List<TraineeEntity> findByBatchId(Long batchId);

    @Query("SELECT t FROM TraineeEntity t WHERE t.user.userId = :userId")
    Optional<TraineeEntity> findByUserId(@Param("userId") Long userId);
}
