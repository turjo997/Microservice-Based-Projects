package com.bjit.trainingmanagementsystem.repository.batch;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Long> {
    List<BatchEntity> findAllByOrderByBatchIdDesc();
}
