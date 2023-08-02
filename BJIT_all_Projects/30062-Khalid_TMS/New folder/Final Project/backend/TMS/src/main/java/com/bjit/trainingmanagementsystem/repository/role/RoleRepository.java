package com.bjit.trainingmanagementsystem.repository.role;

import com.bjit.trainingmanagementsystem.entities.roleEntites.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleName(String roleName);
}
