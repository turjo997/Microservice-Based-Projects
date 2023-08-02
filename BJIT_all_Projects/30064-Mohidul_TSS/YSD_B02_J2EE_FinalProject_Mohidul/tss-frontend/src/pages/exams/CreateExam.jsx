import React, { useState } from 'react';
import axios from 'axios';
import './CreateExam.scss';

const CreateExam = () => {
  const [examName, setExamName] = useState('');
  const [examCode, setExamCode] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!examName.trim() || !examCode.trim()) {
      setErrorMessage('Exam Name and Exam Code cannot be blank.');
      return;
    }

    const examData = {
      examName: examName,
      examCode: examCode,
    };

    const token = localStorage.getItem('token');
    axios.post('http://localhost:8080/exam/', examData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setSuccessMessage('Exam created successfully.');
        setErrorMessage('');
        setExamName('');
        setExamCode('');
      })
      .catch((error) => {
        setErrorMessage('Error creating exam. Please try again.');
        setSuccessMessage('');
      });
  };

  return (
    <div className="create-exam">
      <h2>Create Exam</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="examName">Exam Name:</label>
          <input
            type="text"
            id="examName"
            value={examName}
            onChange={(e) => setExamName(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="examCode">Exam Code:</label>
          <input
            type="text"
            id="examCode"
            value={examCode}
            onChange={(e) => setExamCode(e.target.value)}
            required
          />
        </div>
        <div>
          <button type="submit">Create Exam</button>
        </div>
      </form>
      {successMessage && <p className="success-message">{successMessage}</p>}
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default CreateExam;
