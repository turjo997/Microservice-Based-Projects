package com.bjit.tss.repository;

import com.bjit.tss.entity.ValidationCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<ValidationCodes, Long> {

    Optional<ValidationCodes> findByUserInfoUserId(Long userId);
}