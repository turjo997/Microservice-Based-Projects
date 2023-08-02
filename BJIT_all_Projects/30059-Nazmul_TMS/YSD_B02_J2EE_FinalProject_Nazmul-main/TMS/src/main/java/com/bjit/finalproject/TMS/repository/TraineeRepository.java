package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.TraineeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<TraineeModel, Long> {
    Optional<TraineeModel> findByEmail(String email);
}
