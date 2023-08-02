package com.bjit.trainingmanagementsystem.service.course;

import com.bjit.trainingmanagementsystem.entities.CourseEntity;
import com.bjit.trainingmanagementsystem.factory.CourseEntityBuilder;
import com.bjit.trainingmanagementsystem.factory.CourseEntityFactory;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.course.CourseModel;
import com.bjit.trainingmanagementsystem.repository.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public CourseModel create(CourseModel courseModel) {

        CourseEntityFactory courseEntityFactory = new CourseEntityBuilder()
                .setCourseName(courseModel.getCourseName())
                .setDescription(courseModel.getDescription())
                .setStartDate(courseModel.getStartDate())
                .setEndDate(courseModel.getEndDate())
                .setBatchId(courseModel.getBatchId())
                .setTrainerId(courseModel.getTrainerId());

        CourseEntity courseEntity = courseEntityFactory.createCourseEntity();
        CourseEntity savedCourse = courseRepository.save(courseEntity);

        return modelMapper.map(savedCourse, CourseModel.class);
    }

    @Override
    public CourseModel update(Long courseId, CourseModel courseModel) {

        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with ID: " + courseId));

        BeanUtils.copyProperties(courseModel, courseEntity, getNullPropertyNames(courseModel));
        CourseEntity updatedCourse = courseRepository.save(courseEntity);

        return modelMapper.map(updatedCourse, CourseModel.class);
    }

    @Override
    public List<CourseModel> getAllCourses() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        return courseEntities.stream()
                .map(courseEntity -> modelMapper.map(courseEntity, CourseModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseModel> getScheduleByBatchId(Long batchId) {
        List<CourseEntity> courseEntityList = courseRepository.findByBatchId(batchId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<CourseEntity> sortedCourses = courseEntityList.stream()
                .sorted(Comparator.comparing(course -> {
                    try {
                        return LocalDate.parse(course.getEndDate(), formatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception if necessary
                        return null;
                    }
                }))
                .collect(Collectors.toList());

        return sortedCourses.stream()
                .map(course -> modelMapper.map(course, CourseModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<CourseModel> getCoursesByBatchId(Long batchId) {
        List<CourseEntity> courses = courseRepository.findByBatchId(batchId);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseModel getCourseById(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with ID: " + courseId));
        return modelMapper.map(course, CourseModel.class);
    }

}
