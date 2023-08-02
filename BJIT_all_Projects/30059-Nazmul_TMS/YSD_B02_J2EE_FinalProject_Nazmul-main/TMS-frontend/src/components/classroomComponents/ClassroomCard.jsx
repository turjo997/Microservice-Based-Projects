import React from 'react';
import { Link } from 'react-router-dom';

const ClassroomCard = ({ classroomName,classroomId }) => {  

return (
    <div className="card" style={{display:'flex', marginLeft:'70px', marginTop:'40px', width:'400px'}}>
      <h3>{classroomName}</h3>
      <Link to={`/classrooms/${encodeURIComponent(classroomId)}`}>
        <button>Go to Classroom</button>
      </Link>
    </div>
  );
};

export default ClassroomCard;
