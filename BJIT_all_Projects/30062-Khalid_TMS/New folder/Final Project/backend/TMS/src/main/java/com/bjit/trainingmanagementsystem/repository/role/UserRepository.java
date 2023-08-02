package com.bjit.trainingmanagementsystem.repository.role;

import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);

}
