import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';


const getToken = () => {
  const token = localStorage.getItem("token");
  return token;
};

const UploadWrittenMarks = () => {
  const [evaluatorId, setEvaluatorId] = useState(null);
  const [marksData, setMarksData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const storedEvaluatorId = localStorage.getItem('evaluatorId');
    setEvaluatorId(storedEvaluatorId);

    if (storedEvaluatorId) {
      axios.get(`http://localhost:8084/evaluator/marks/${storedEvaluatorId}`, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      })
        .then(response => {
          setMarksData(response.data);
          setIsLoading(false);
        })
        .catch(error => {
          console.error('Error fetching data:', error);
          setIsLoading(false);
        });
    }
  }, []);

  const handleWrittenMarksChange = (paperCode, writtenMarks) => {
    setMarksData(prevMarksData => {
      return prevMarksData.map(data => {
        if (data.paperCode === paperCode) {
          return { ...data, writtenMarks };
        }
        return data;
      });
    });
  };

  const handleUploadWrittenMarks = (paperCode) => {
    const paperData = marksData.find(data => data.paperCode === paperCode);
    if (paperData) {
      axios.post(`http://localhost:8084/evaluator/writtenMark/${paperCode}`, {
        writtenMarks: paperData.writtenMarks
      },{
        headers: {
          Authorization: `Bearer ${getToken()}`,
          'Content-Type': 'application/json',

        },
      })
      .then(response => {
        console.log('Upload successful:', response.data);
        toast.success("Mark Uploaded");
      })
      .catch(error => {
        console.error('Upload failed:', error);
      });
    }
  };

  return (
    <div>
      <h2>Written Marks Upload</h2>
      {isLoading ? (
        <p>Loading marks data...</p>
      ) : marksData.length > 0 ? (
        <div>
          {marksData.map(data => (
            <div key={data.markId}>
              <p>Paper Code: {data.paperCode}</p>
              <label>
                Written Marks:
                <input
                  type="text"
                  value={data.writtenMarks || ''}
                  onChange={(e) => handleWrittenMarksChange(data.paperCode, e.target.value)}
                />
              </label>
              <button onClick={() => handleUploadWrittenMarks(data.paperCode)}>Upload</button>
            </div>
          ))}
        </div>
      ) : (
        <p>No marks data available for the evaluator.</p>
      )}
    </div>
  );
};

export default UploadWrittenMarks;
