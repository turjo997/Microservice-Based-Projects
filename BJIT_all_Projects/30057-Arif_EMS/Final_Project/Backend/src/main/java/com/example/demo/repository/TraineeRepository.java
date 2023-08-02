package com.example.demo.repository;

import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee , Long> {

    public Trainee findByUser( User user );

}
