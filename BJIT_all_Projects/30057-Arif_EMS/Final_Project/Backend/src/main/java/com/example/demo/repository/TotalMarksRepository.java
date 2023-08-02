package com.example.demo.repository;

import com.example.demo.entity.TotalMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalMarksRepository extends JpaRepository<TotalMarks, Long> {

}
