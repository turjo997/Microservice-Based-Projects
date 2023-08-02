package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.Circular;
import com.bjit.TraineeSelection.model.CircularDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CircularRepository extends JpaRepository<Circular,Long> {


    List<Circular> findByStatus(String active);

}
