package com.bjit.trainingmanagementsystem.repository.role;

import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {

    @Query("SELECT t FROM TrainerEntity t WHERE t.user.userId = :userId")
    Optional<TrainerEntity> findByUserId(@Param("userId") Long userId);
}
