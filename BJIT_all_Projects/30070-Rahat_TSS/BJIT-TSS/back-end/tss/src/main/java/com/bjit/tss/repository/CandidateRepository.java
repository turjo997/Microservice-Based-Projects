package com.bjit.tss.repository;

import com.bjit.tss.entity.CandidateMarks;
import com.bjit.tss.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateMarks, Long> {

    Optional<CandidateMarks> findByExamineeInfoExamineeId(Long examineeId);

    List<CandidateMarks> findAllByExamineeInfoRoleAndExamineeInfoCourseInfoBatchCode(Role role, String batchCode);
    
    List<CandidateMarks> findAllByExamineeInfoRoleAndExamineeInfoCourseInfoBatchCodeAndWrittenMarksEvaluatorInfoIsNull(Role role, String batchCode);

    List<CandidateMarks> findAllByAptitudeTestPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(boolean b, String batchCode, boolean b1);

    List<CandidateMarks> findAllByWrittenMarksPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(boolean b, String batchCode, boolean b1);

    List<CandidateMarks> findAllByTechnicalVivaPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(boolean b, String batchCode, boolean b1);

    List<CandidateMarks> findAllByHrVivaPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(boolean b, String batchCode, boolean b1);

    List<CandidateMarks> findAllByExamineeInfoRole(Role role);

    List<CandidateMarks> findAllByExamineeInfoUserInfoUserIdAndExamineeInfoCourseInfoIsAvailableAndExamineeInfoRole(Long userId, boolean b, Role role);

    List<CandidateMarks> findAllByHrVivaPassedAndTechnicalVivaPassedAndAptitudeTestPassedAndWrittenMarksPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoRole(boolean b, boolean b1, boolean b2, boolean b3, String batchCode, Role role);
}