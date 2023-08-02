package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.dto.courseDto.CourseCreateDTO;
import com.bjit.finalproject.TMS.dto.courseDto.CourseResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    CourseResponseDTO createCourse(CourseCreateDTO courseCreateDTO);
    List<CourseResponseDTO> getCourses();
}
