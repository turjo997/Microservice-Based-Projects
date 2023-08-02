import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './CircularShow.scss';
import axios from 'axios';

const CircularShow = () => {
  const [circularData, setCircularData] = useState([]);

  useEffect(() => {
    fetchCircularData();
  }, []);

  const fetchCircularData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/circular/');
      setCircularData(response.data);
    } catch (error) {
      console.error('Error fetching circular data', error);
    }
  };

  // Make sure circularData is defined and is an array
  if (!circularData || !Array.isArray(circularData) || circularData.length === 0) {
    return (
      <div className="circular-show">
        <h1>No circular data available</h1>
      </div>
    );
  }

  return (
    <div className="circular-show">
      {circularData.map((circular) => (
        <div className="circular-card" key={circular.circularId}>
          <h3>{circular.title}</h3>
          <p>{circular.description}</p>
          <Link to={`/apply/${circular.circularId}`}>
            <button>Apply</button>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default CircularShow;
