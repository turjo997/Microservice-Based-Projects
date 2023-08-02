package com.bjit.trainingmanagementsystem.repository.role;

import com.bjit.trainingmanagementsystem.entities.roleEntites.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}
