package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.course.CourseModel;
import com.bjit.trainingmanagementsystem.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseModel> createCourse(@RequestBody CourseModel courseModel){
        return new ResponseEntity<>(courseService.create(courseModel), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<CourseModel> updateCourse(@PathVariable Long courseId, @RequestBody CourseModel courseModel) {
        return new ResponseEntity<>(courseService.update(courseId, courseModel), HttpStatus.OK);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<CourseModel>> getCoursesByBatchId(@PathVariable Long batchId) {
        List<CourseModel> courses = courseService.getCoursesByBatchId(batchId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseModel> getCourseById(@PathVariable Long courseId) {
        CourseModel course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/schedule/{batchId}")
    public ResponseEntity<List<CourseModel>> getScheduleByBatchId(@PathVariable Long batchId) {
        List<CourseModel> courses = courseService.getScheduleByBatchId(batchId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


}

