package com.bjit.trainingmanagementsystem;


import com.bjit.trainingmanagementsystem.entities.CourseEntity;
import com.bjit.trainingmanagementsystem.factory.CourseEntityFactory;
import com.bjit.trainingmanagementsystem.models.course.CourseModel;
import com.bjit.trainingmanagementsystem.repository.course.CourseRepository;
import com.bjit.trainingmanagementsystem.service.course.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    private CourseRepository courseRepository;
    private ModelMapper modelMapper;
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        modelMapper = mock(ModelMapper.class);
        courseService = new CourseServiceImpl(courseRepository, modelMapper);
    }

    @Test
    void testCreateCourse() {

        CourseModel inputCourseModel = new CourseModel();
        inputCourseModel.setCourseName("Test Course");

        CourseEntityFactory courseEntityFactory = mock(CourseEntityFactory.class);
        CourseEntity createdCourseEntity = new CourseEntity();

        when(courseEntityFactory.createCourseEntity()).thenReturn(createdCourseEntity);

        CourseEntity savedCourseEntity = new CourseEntity();

        when(courseRepository.save(any(CourseEntity.class))).thenReturn(savedCourseEntity);

        CourseModel expectedCourseModel = new CourseModel();
        when(modelMapper.map(savedCourseEntity, CourseModel.class)).thenReturn(expectedCourseModel);

        CourseModel result = courseService.create(inputCourseModel);
        assertEquals(expectedCourseModel, result);

    }
}
