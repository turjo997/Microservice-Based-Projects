package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.BatchTrainees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchTraineeRepository extends JpaRepository<BatchTrainees, Long> {
    List<BatchTrainees> findAllByBatchName(String batchName);
    Optional<BatchTrainees> findByTraineeEmail(String traineeEmail);
    boolean existsByTraineeEmail(String traineeEmail);
}
