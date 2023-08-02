package com.bjit.tss.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseModel {

    @Valid

    @NotEmpty(message = "Course Name is required")
    private String courseName;

    @NotNull(message = "Application Deadline is required")
    private Date applicationDeadline;

    @NotNull(message = "Start Date is required")
    private Date startDate;

    @NotNull(message = "End Date is required")
    private Date endDate;

    @NotEmpty(message = "Batch Code is required")
    private String batchCode;

    @NotEmpty(message = "Course Description is required")
    private String courseDescription;

    @NotNull(message = "Is The Available is required")
    private Boolean isAvailable;

    @NotNull(message = "Vacancy is required")
    private Long vacancy;

    @NotNull(message = "Written Exam Time is required")
    private Date writtenExamTime;

    @NotEmpty(message = "Applicant Dashboard Message is required")
    private String applicantDashboardMessage;

    @NotEmpty(message = "Written Shortlisted Dashboard Message is required")
    private String writtenShortlistedDashboardMessage;

    @NotEmpty(message = "Written Passed Dashboard Message is required")
    private String writtenPassedDashboardMessage;

    @NotEmpty(message = "Aptitude Test Passed Dashboard Message is required")
    private String aptitudeTestPassedDashboardMessage;

    @NotEmpty(message = "Technical Viva Passed Dashboard Message is required")
    private String technicalVivaPassedDashboardMessage;

    @NotEmpty(message = "HR Viva Passed Dashboard Message is required")
    private String hrVivaPassedDashboardMessage;

    @NotEmpty(message = "Trainee Dashboard Message is required")
    private String traineeDashboardMessage;

    @NotEmpty(message = "Written Exam Instruction is required")
    private String writtenExamInstruction;
}
