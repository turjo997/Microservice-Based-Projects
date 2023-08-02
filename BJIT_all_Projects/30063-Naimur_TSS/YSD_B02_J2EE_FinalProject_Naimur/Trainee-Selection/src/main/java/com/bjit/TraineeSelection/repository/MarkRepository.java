package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.Circular;
import com.bjit.TraineeSelection.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Marks,Long> {

    Marks findByApplicantId(Long applicantId);

    boolean existsByApplicantIdAndCircularId(Long applicantId, Long circularId);


    List<Marks> findByStatus(long l);

    List<Marks> findByCircularId(Long circularId);

    Marks findMarksByPaperCode(Long paperCode);
}
