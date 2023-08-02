package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.TrainerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerModel, Long> {
    Optional<TrainerModel> findByEmail(String email);
}
