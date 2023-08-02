package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.dto.courseDto.CourseCreateDTO;
import com.bjit.finalproject.TMS.dto.courseDto.CourseResponseDTO;
import com.bjit.finalproject.TMS.exception.AlreadyExistsException;
import com.bjit.finalproject.TMS.model.Course;
import com.bjit.finalproject.TMS.repository.CourseRepository;
import com.bjit.finalproject.TMS.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepo;
//    private final UserRepository userRepo;
    
    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseCreateDTO courseCreateDTO) {
        Optional<Course> getCourse = courseRepo.findByCourseName(courseCreateDTO.getCourseName());
        if(getCourse.isPresent()){
            throw new AlreadyExistsException("Course already exists");
        }
        Course createdCourse = Course.builder()
                              .courseName(courseCreateDTO.getCourseName())
                              .courseDescription(courseCreateDTO.getCourseDescription())
                              .build();
        courseRepo.save(createdCourse);
        CourseResponseDTO response = CourseResponseDTO.builder()
                                    .courseTitle(createdCourse.getCourseName())
                                    .courseDescription(createdCourse.getCourseDescription())
                                    .build();
        return response;
    }

    @Override
    public List<CourseResponseDTO> getCourses() {
        List<Course> allCourses = courseRepo.findAll();
        List<CourseResponseDTO> responses = new ArrayList<>();
        for(Course course:allCourses){
            CourseResponseDTO response = CourseResponseDTO.builder()
                                            .id(course.getCourseId())
                                            .courseTitle(course.getCourseName())
                                            .courseDescription(course.getCourseDescription())
                                            .build();
            responses.add(response);
        }
        return responses;
    }
}
