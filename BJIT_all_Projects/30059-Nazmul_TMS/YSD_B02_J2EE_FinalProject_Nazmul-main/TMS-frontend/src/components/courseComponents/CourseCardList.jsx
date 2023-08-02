import React from 'react';
import CourseCard from './CourseCard';
import './cardlist.css';

const CourseCardList = ({ courses }) => {
  return (
    <div>
      <div className="course-list">
        {courses.map((course) => (
          <CourseCard key={course.courseId} course={course} />
        ))}
      </div>
    </div>
  );
};

export default CourseCardList;
