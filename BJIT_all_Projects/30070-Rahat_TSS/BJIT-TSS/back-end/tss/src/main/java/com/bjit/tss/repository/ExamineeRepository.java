package com.bjit.tss.repository;

import com.bjit.tss.entity.ExamineeInfo;
import com.bjit.tss.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamineeRepository extends JpaRepository<ExamineeInfo, Long> {

    Optional<ExamineeInfo> findByUserInfoUserIdAndCourseInfoCourseId(Long userId, Long courseId);

    Optional<List<ExamineeInfo>> findByRoleAndCourseInfoIsAvailableAndCourseInfoBatchCode(Role role, Boolean isAvailable, String batchCode);

    List<ExamineeInfo> findByRole(Role role);

    List<ExamineeInfo> findAllByUserInfoUserIdAndCourseInfoIsAvailable(Long userId, boolean isAvailable);

    List<ExamineeInfo> findDistinctByUserInfoEducationalInstituteAndRoleAndCourseInfoIsAvailableAndCourseInfoBatchCode(String educationalInstitute, Role role, boolean b, String batchCode);

    @Query("SELECT DISTINCT e.userInfo.educationalInstitute FROM ExamineeInfo e WHERE e.role = ?1 AND e.courseInfo.isAvailable = ?2 AND e.courseInfo.batchCode = ?3")
    List<String> findDistinctEducationalInstitutesByRoleAndCourseInfoIsAvailableAndCourseInfoBatchCode(Role role, boolean isAvailable, String batchCode);
}