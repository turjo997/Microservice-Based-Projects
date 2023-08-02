package com.bjit.tss.repository;

import com.bjit.tss.entity.WrittenMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrittenMarksRepository extends JpaRepository<WrittenMarks, Long> {

}