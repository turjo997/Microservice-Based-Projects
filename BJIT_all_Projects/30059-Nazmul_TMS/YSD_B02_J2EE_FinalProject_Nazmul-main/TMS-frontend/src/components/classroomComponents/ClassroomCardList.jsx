import React, { useState, useEffect } from 'react';
import ClassroomCard from './ClassroomCard';

const ClassroomCardList = ({classrooms}) => {
  
//   useEffect(() => {
//     // Fetch the list of classroom titles from your Spring backend API
//     // Example: replace 'YOUR_API_ENDPOINT' with the actual endpoint URL
//     fetch('YOUR_API_ENDPOINT')
//       .then((response) => response.json())
//       .then((data) => setClassrooms(data))
//       .catch((error) => console.error('Error fetching classroom titles:', error));
//   }, []);
  return (
    <div>
      <h1>Classrooms</h1>
      {classrooms.map((classroom) => (
        <ClassroomCard key={classroom.classroomId} classroomId={classroom.classroomId} classroomName={classroom.classroomName} />
      ))}
    </div>
  );
};

export default ClassroomCardList;
