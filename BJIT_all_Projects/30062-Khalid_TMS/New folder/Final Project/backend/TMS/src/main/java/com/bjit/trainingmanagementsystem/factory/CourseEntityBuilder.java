package com.bjit.trainingmanagementsystem.factory;

import com.bjit.trainingmanagementsystem.entities.CourseEntity;

public class CourseEntityBuilder extends CourseEntityFactory {
    private String courseName;
    private String description;
    private String startDate;
    private String endDate;
    private Long trainerId;
    private Long batchId;

    public CourseEntityBuilder setCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public CourseEntityBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CourseEntityBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public CourseEntityBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public CourseEntityBuilder setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
        return this;
    }

    public CourseEntityBuilder setBatchId(Long batchId) {
        this.batchId = batchId;
        return this;
    }


    @Override
    public CourseEntity createCourseEntity() {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseName(courseName);
        courseEntity.setDescription(description);
        courseEntity.setStartDate(startDate);
        courseEntity.setEndDate(endDate);
        courseEntity.setTrainerId(trainerId);
        courseEntity.setBatchId(batchId);

        return courseEntity;
    }
}
