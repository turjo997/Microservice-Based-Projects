package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    Optional<Batch> findByBatchName(String batchName);
//    List<Batch> findAllByBatchName(String batchName);
}
