import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './UploadMark.scss';

const UploadMark = () => {
  const [examOptions, setExamOptions] = useState([]);
  const [circularOptions, setCircularOptions] =useState([]);
  const [examId, setExamId] = useState('');
  const [circular, setCircular] = useState('');
  const [applicantId, setApplicantId] = useState('');
  const [mark, setMark] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [allMarks, setAllMarks] = useState([]);
  const [marksByApplicant, setMarksByApplicant] = useState([]);
  const [marksByCircular, setMarksByCircular] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/exam/', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setExamOptions(response.data);
      })
      .catch((error) => {
        console.error('Error fetching exam options:', error);
      });
  }, []);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/circular/', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setCircularOptions(response.data);
      })
      .catch((error) => {
        console.error('Error fetching circular options:', error);
      });
  }, []);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/mark/all', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setAllMarks(response.data);
      })
      .catch((error) => {
        console.error('Error fetching all marks:', error);
      });
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!examId || !circular || !applicantId || !mark) {
      setErrorMessage('All fields are required.');
      return;
    }

    const markData = {
      exam: { examId },
      circular,
      applicantId,
      mark,
    };

    const token = localStorage.getItem('token');
    axios.post('http://localhost:8080/mark/save', markData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setSuccessMessage('Mark uploaded successfully.');
        setErrorMessage('');

        setExamId('');
        setCircular('');
        setApplicantId('');
        setMark('');

        fetchMarksByApplicant(applicantId);
        fetchMarksByCircular(circular);
        fetchAllMarks();
      })
      .catch((error) => {
        setErrorMessage('You have already uploaded the marks previously.');
        setSuccessMessage('');
      });
  };

  const fetchMarksByApplicant = (applicantId) => {
    if (applicantId.trim() !== '') {
      const token = localStorage.getItem('token');
      axios.get(`http://localhost:8080/mark/applicant/${applicantId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((response) => {
          setMarksByApplicant(response.data);
        })
        .catch((error) => {
          console.error('Error fetching marks by applicant ID:', error);
        });
    }
  };

  const fetchMarksByCircular = (circular) => {
    if (circular.trim() !== '') {
      const token = localStorage.getItem('token');
      axios.get(`http://localhost:8080/mark/circular/${circular}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((response) => {
          setMarksByCircular(response.data);
        })
        .catch((error) => {
          console.error('Error fetching marks by circular:', error);
        });
    }
  };

  const fetchAllMarks = () => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/mark/all', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setAllMarks(response.data);
      })
      .catch((error) => {
        console.error('Error fetching all marks:', error);
      });
  };

  return (
    <div className="upload-mark">
      <h2>Upload Marks of Exams</h2>
      <form onSubmit={handleSubmit} className="upload-mark__form">
      <div>
          <label htmlFor="applicantId">Applicant ID:</label>
          <input
            type="text"
            id="applicantId"
            value={applicantId}
            onChange={(e) => setApplicantId(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="examId">Exam:</label>
          <select
            id="examId"
            value={examId}
            onChange={(e) => setExamId(e.target.value)}
            required
          >
            <option value="">Select an exam</option>
            {examOptions.map((exam) => (
              <option key={exam.examId} value={exam.examId}>
                {exam.examName} (Code: {exam.examCode})
              </option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="circular">Circular:</label>          
          <select
            type="text"
            id="circular"
            value={circular}
            onChange={(e) => setCircular(e.target.value)}
            required
          >
            <option value="">Select an circular</option>
            {circularOptions.map((circular) => (
              <option key={circular.circularId} value={circular.title}>
                {circular.title} 
              </option>
            ))}
          </select>
        </div>
        
        <div>
          <label htmlFor="mark">Mark:</label>
          <input
            type="text"
            id="mark"
            value={mark}
            onChange={(e) => setMark(e.target.value)}
            required
          />
        </div>
        <div>
          <button type="submit">Upload Mark</button>
        </div>
      </form>

      {successMessage && <p className="success-message">{successMessage}</p>}
      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <div className="upload-mark__table">
        <h2>All Marks</h2>
        <table>
          <thead>
            <tr>
              <th>Mark ID</th>
              <th>Exam Name</th>
              <th>Exam Code</th>
              <th>Circular</th>
              <th>Applicant ID</th>
              <th>Mark</th>
            </tr>
          </thead>
          <tbody>
            {allMarks.map((mark) => (
              <tr key={mark.markId}>
                <td>{mark.markId}</td>
                <td>{mark.exam.examName}</td>
                <td>{mark.exam.examCode}</td>
                <td>{mark.circular}</td>
                <td>{mark.applicantId}</td>
                <td>{mark.mark}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="upload-mark__table">
        <h2>Marks by Applicant ID</h2>
        <div className="upload-mark__search">
          <input
            type="text"
            placeholder="Enter Applicant ID"
            value={applicantId}
            onChange={(e) => setApplicantId(e.target.value)}
            required
          />
          <button onClick={() => fetchMarksByApplicant(applicantId)}>Search</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>Mark ID</th>
              <th>Exam Name</th>
              <th>Exam Code</th>
              <th>Circular</th>
              <th>Applicant ID</th>
              <th>Mark</th>
            </tr>
          </thead>
          <tbody>
            {marksByApplicant.map((mark) => (
              <tr key={mark.markId}>
                <td>{mark.markId}</td>
                <td>{mark.exam.examName}</td>
                <td>{mark.exam.examCode}</td>
                <td>{mark.circular}</td>
                <td>{mark.applicantId}</td>
                <td>{mark.mark}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="upload-mark__table">
        <h2>Marks by Circular</h2>
        <div className="upload-mark__search">
          <input
            type="text"
            placeholder="Enter Circular"
            value={circular}
            onChange={(e) => setCircular(e.target.value)}
            required
          />
          <button onClick={() => fetchMarksByCircular(circular)}>Search</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>Mark ID</th>
              <th>Exam Name</th>
              <th>Exam Code</th>
              <th>Circular</th>
              <th>Applicant ID</th>
              <th>Mark</th>
            </tr>
          </thead>
          <tbody>
            {marksByCircular.map((mark) => (
              <tr key={mark.markId}>
                <td>{mark.markId}</td>
                <td>{mark.exam.examName}</td>
                <td>{mark.exam.examCode}</td>
                <td>{mark.circular}</td>
                <td>{mark.applicantId}</td>
                <td>{mark.mark}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UploadMark;
