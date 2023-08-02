package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.Role;
import com.bjit.finalproject.TMS.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    List<UserModel> findTraineesByRole(Role role);
}
