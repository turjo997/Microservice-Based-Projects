package com.bjit.tss.mapper;

import com.bjit.tss.entity.CourseInfo;
import com.bjit.tss.model.request.CourseModel;

public class CourseMapper {

    public static CourseInfo mapToCourseInfo(CourseModel courseModel) {
        return CourseInfo.builder()
                .courseName(courseModel.getCourseName())
                .applicationDeadline(courseModel.getApplicationDeadline())
                .startDate(courseModel.getStartDate())
                .endDate(courseModel.getEndDate())
                .batchCode(courseModel.getBatchCode())
                .courseDescription(courseModel.getCourseDescription())
                .isAvailable(courseModel.getIsAvailable())
                .vacancy(courseModel.getVacancy())
                .writtenExamTime(courseModel.getWrittenExamTime())
                .applicantDashboardMessage(courseModel.getApplicantDashboardMessage())
                .writtenShortlistedDashboardMessage(courseModel.getWrittenShortlistedDashboardMessage())
                .writtenPassedDashboardMessage(courseModel.getWrittenPassedDashboardMessage())
                .aptitudeTestPassedDashboardMessage(courseModel.getAptitudeTestPassedDashboardMessage())
                .technicalVivaPassedDashboardMessage(courseModel.getTechnicalVivaPassedDashboardMessage())
                .traineeDashboardMessage(courseModel.getTraineeDashboardMessage())
                .hrVivaPassedDashboardMessage(courseModel.getHrVivaPassedDashboardMessage())
                .writtenExamInstruction(courseModel.getWrittenExamInstruction())
                .build();
    }
}