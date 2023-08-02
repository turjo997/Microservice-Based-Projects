package com.bjit.tss.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "course_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;
    private String courseName;
    private Date applicationDeadline;
    private Date writtenExamTime;
    private Date startDate;
    private Date endDate;
    private String batchCode;
    private Boolean isAvailable;
    private Long vacancy;
    @Column(length = 1000)
    private String courseDescription;
    @Column(length = 1000)
    private String applicantDashboardMessage;
    @Column(length = 1000)
    private String writtenShortlistedDashboardMessage;
    @Column(length = 1000)
    private String writtenPassedDashboardMessage;
    @Column(length = 1000)
    private String technicalVivaPassedDashboardMessage;
    @Column(length = 1000)
    private String aptitudeTestPassedDashboardMessage;
    @Column(length = 1000)
    private String hrVivaPassedDashboardMessage;
    @Column(length = 1000)
    private String traineeDashboardMessage;
    @Column(length = 1000)
    private String writtenExamInstruction;

}