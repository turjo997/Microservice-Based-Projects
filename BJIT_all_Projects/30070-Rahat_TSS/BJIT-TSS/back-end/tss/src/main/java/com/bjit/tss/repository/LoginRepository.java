package com.bjit.tss.repository;

import com.bjit.tss.entity.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginInfo, Long> {
    Optional<LoginInfo> findByEmail(String email);
}