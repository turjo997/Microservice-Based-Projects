package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.courseDto.CourseCreateDTO;
import com.bjit.finalproject.TMS.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;
    @PostMapping("/create-course")
    public ResponseEntity<Object> createCourse(@RequestBody CourseCreateDTO courseDTO){
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.OK);
    }
    @GetMapping("/get-courses")
    public ResponseEntity<Object> getCourses(){
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }
}
