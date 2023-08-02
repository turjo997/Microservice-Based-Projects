package com.bjit.trainingmanagementsystem.service.course;

import com.bjit.trainingmanagementsystem.entities.CourseEntity;
import com.bjit.trainingmanagementsystem.models.course.CourseModel;

import java.util.List;

public interface CourseService {
    CourseModel create(CourseModel courseModel);
    CourseModel update(Long courseId, CourseModel courseModel);
    List<CourseModel> getCoursesByBatchId(Long batchId);
    CourseModel getCourseById(Long courseId);

    List<CourseModel> getAllCourses();

    List<CourseModel> getScheduleByBatchId(Long batchId);
}
