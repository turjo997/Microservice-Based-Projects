import React, { useState, useEffect } from 'react';
import './CircularShow.scss';
import axios from 'axios';

const CircularShow = () => {
  const [circularData, setCircularData] = useState([]);

  useEffect(() => {
    fetchCircularData();
  }, []);

  const fetchCircularData = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('http://localhost:8080/circular/', {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
      setCircularData(response.data);
    } catch (error) {
      console.error('Error fetching circular data', error);
    }
  };

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
        </div>
      ))}
    </div>
  );
};

export default CircularShow;
