import React from 'react';
import { Card } from 'react-bootstrap';
import javaImage from './images/java.jpg';
import pythonImage from './images/python.jpg';
import sqaImage from './images/sqa.jpg';
import notFoundImage from './images/no.jpg';
import scrumImage from './images/scrum.jpg'

const CourseCard = ({ course }) => {
  const { courseTitle, courseDescription } = course;
  console.log(course);
  // Mapping of course names to image filenames
  const courseImages = {
    java:  javaImage,
    python: pythonImage,
    sqa: sqaImage,
    scrum:scrumImage,
    // Add more mappings as needed
  };

const imageUrl = courseImages[course.courseTitle] || notFoundImage;
  return (
    <Card style={{ width: '18rem' }}>
      <Card.Img variant="top" src={imageUrl} />
      <Card.Body>
        <Card.Title>{courseTitle}</Card.Title>
        <Card.Text>{courseDescription}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default CourseCard;
