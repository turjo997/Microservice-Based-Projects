import React, { useState, useEffect } from 'react';
import { Table } from 'reactstrap';

const FinalSelection = () => {
  const [candidatesData, setCandidatesData] = useState([]);
  const [sortCriteria, setSortCriteria] = useState('circularId'); 
  const [sortOrder, setSortOrder] = useState('asc'); 

  useEffect(() => {
  
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8084/admin/finalCandidates', {
          headers: {
            Authorization: `Bearer ${token}` 
          }
        });
        const data = await response.json();
        setCandidatesData(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData(); 
  }, []);

  const handleSort = (criteria) => {
    if (sortCriteria === criteria) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {

      setSortCriteria(criteria);
      setSortOrder('asc');
    }
  };

  const sortedCandidatesData = candidatesData.sort((a, b) => {
    if (sortOrder === 'asc') {
      return a[sortCriteria] - b[sortCriteria];
    } else {
      return b[sortCriteria] - a[sortCriteria];
    }
  });

  return (
    <div>
      <h1>Final Selection </h1>
      {candidatesData.length > 0 ? (
        <Table striped>
          <thead>
            <tr>
              <th onClick={() => handleSort('circularId')}>
                Circular ID {sortCriteria === 'circularId' && (sortOrder === 'asc' ? '▲' : '▼')}
              </th>
              <th>Applicant ID</th>
              <th>Status</th>
              <th onClick={() => handleSort('writtenMarks')}>
                Written Marks {sortCriteria === 'writtenMarks' && (sortOrder === 'asc' ? '▲' : '▼')}
              </th>
              <th onClick={() => handleSort('technicalMarks')}>
                Technical Marks {sortCriteria === 'technicalMarks' && (sortOrder === 'asc' ? '▲' : '▼')}
              </th>
              <th onClick={() => handleSort('hrMarks')}>
                HR Marks {sortCriteria === 'hrMarks' && (sortOrder === 'asc' ? '▲' : '▼')}
              </th>
              <th onClick={() => handleSort('totalScore')}>
                Total Score {sortCriteria === 'totalScore' && (sortOrder === 'asc' ? '▲' : '▼')}
              </th>
            </tr>
          </thead>
          <tbody>
            {sortedCandidatesData.map((candidate) => (
              <tr key={candidate.markId}>
                <td>{candidate.circularId}</td>
                <td>{candidate.applicantId}</td>
                <td>{candidate.status}</td>
                <td>{candidate.writtenMarks}</td>
                <td>{candidate.technicalMarks}</td>
                <td>{candidate.hrMarks}</td>
                <td>{candidate.totalScore}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      ) : (
        <p>No candidates data available</p>
      )}
    </div>
  );
};

export default FinalSelection;
