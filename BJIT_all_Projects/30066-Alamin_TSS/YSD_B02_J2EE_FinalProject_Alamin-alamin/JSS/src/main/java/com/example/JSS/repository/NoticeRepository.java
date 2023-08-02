package com.example.JSS.repository;

import com.example.JSS.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByApplicationsApplicantId(Long applicantId);
    List<Notice> findByApplicationsIsNull();
}
