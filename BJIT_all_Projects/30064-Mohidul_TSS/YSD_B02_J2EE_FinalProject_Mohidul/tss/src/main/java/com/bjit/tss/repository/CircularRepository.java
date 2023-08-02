package com.bjit.tss.repository;

import com.bjit.tss.entity.CircularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularRepository extends JpaRepository<CircularEntity, Long> {
}
