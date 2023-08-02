package com.bjit.tss.repository;

import com.bjit.tss.entity.WrittenQuestionMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WrittenQuestionMarksRepository extends JpaRepository<WrittenQuestionMarks, Long> {

}