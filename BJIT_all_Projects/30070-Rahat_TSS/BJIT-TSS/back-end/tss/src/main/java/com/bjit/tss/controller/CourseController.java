package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.CourseModel;
import com.bjit.tss.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getCourses() {
        return courseService.allCourses();
    }

    @GetMapping("/unavailable")
    public ResponseEntity<ApiResponse<?>> getUnavailableCourses() {
        return courseService.getUnavailableCourses();
    }

    @GetMapping("/batch-code/{batchCode}")
    public ResponseEntity<ApiResponse<?>> getCourse(@PathVariable String batchCode) {
        return courseService.getCourse(batchCode);
    }

    @PostMapping("/update/batch_code/{batchCode}")
    public ResponseEntity<ApiResponse<?>> updateCourse(@Valid @PathVariable String batchCode, @RequestBody CourseModel courseModel) {
        return courseService.updateCourse(batchCode, courseModel);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createCourse(@Valid @RequestBody CourseModel courseModel) {
        return courseService.createCourse(courseModel);
    }
}
