package com.bjit.tss.service.impl;

import com.bjit.tss.entity.CourseInfo;
import com.bjit.tss.entity.LoginInfo;
import com.bjit.tss.exception.CourseException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.mapper.CourseMapper;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.CourseModel;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.CourseRepository;
import com.bjit.tss.enums.Role;
import com.bjit.tss.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> createCourse(CourseModel courseModel) {
        Optional<CourseInfo> course = courseRepository.findByBatchCode(courseModel.getBatchCode());
        if (course.isPresent()) {
            throw new CourseException("A course already exists with the batch code " + courseModel.getBatchCode());
        }

        CourseInfo courseInfo = CourseMapper.mapToCourseInfo(courseModel);
        CourseInfo savedCourse = courseRepository.save(courseInfo);
        System.out.println("A course was created with the batch code : "+savedCourse.getBatchCode());
        return ApiResponseMapper.mapToResponseEntityCreated(savedCourse, "Course created successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> allCourses() {
        List<CourseInfo> courseInfoList = courseRepository.findByIsAvailable(true);
        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(courseInfoList.size())
                .listResponse(courseInfoList)
                .build();
        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getCourse(String batchCode) {
        LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CourseInfo> courseInfo= Optional.of(new CourseInfo());

        if (loginInfo.getRole()== Role.ADMIN){
            courseInfo = courseRepository.findByBatchCode(batchCode);
        } else {
            courseInfo = courseRepository.findByBatchCodeAndIsAvailable(batchCode, true);
        }

        if (courseInfo.isEmpty()) {
            throw new CourseException("Invalid Batch Code : " + batchCode);
        }

        return ApiResponseMapper.mapToResponseEntityOK(courseInfo);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> updateCourse(String batchCode, CourseModel courseModel) {
        Optional<CourseInfo> course = courseRepository.findByBatchCode(batchCode);
        if (course.isEmpty()) {
            throw new CourseException("Invalid Batch Code : " + batchCode);
        }

        Optional<CourseInfo> courseModelCode = courseRepository.findByBatchCode(courseModel.getBatchCode());
        if (courseModelCode.isPresent() && !Objects.equals(batchCode.toUpperCase(), courseModel.getBatchCode().toUpperCase())) {
            throw new CourseException("This batch code : " + courseModel.getBatchCode() + " you want to update to already exists");
        }

        CourseInfo courseInfo = CourseMapper.mapToCourseInfo(courseModel);
        courseInfo.setCourseId(course.get().getCourseId());
        CourseInfo savedCourse = courseRepository.save(courseInfo);
        return ApiResponseMapper.mapToResponseEntityOK(savedCourse, "Successfully Updated");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getUnavailableCourses() {
        List<CourseInfo> courseInfoList = courseRepository.findByIsAvailable(false);
        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(courseInfoList.size())
                .listResponse(courseInfoList)
                .build();
        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }
}
