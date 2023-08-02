import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';

const getToken = () => {
  const token = localStorage.getItem('token');
  return token;
};

const ViewApplication = () => {
  const { circularId } = useParams();
  const [applicantData, setApplicantData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const [sortOrder, setSortOrder] = useState({
    educationalInstitute: 'asc',
    degreeName: 'asc',
    cgpa: 'asc',
    gender: 'asc',
    passingYear: 'asc',
  });

  const handleSorting = (field) => {
    setSortOrder((prevSortOrder) => ({
      ...prevSortOrder,
      [field]: prevSortOrder[field] === 'asc' ? 'desc' : 'asc',
    }));
  };

  const handleButtonClick = (applicationId) => {
    axios
      .put(`http://localhost:8084/admin/application/approve/${applicationId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
          'Content-Type': 'application/json',
        },
      })
      .then((response) => {
        console.log('Application approved successfully!');
        toast.success("Application is Approved");
      })
      .catch((error) => {
        console.error('Error approving application:', error);
      });
  };

  useEffect(() => {
    axios
      .get(`http://localhost:8084/admin/application/${circularId}`, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      })
      .then((response) => {
        const responseDataKey = Object.keys(response.data)[0]; // Get the dynamic key
        const applicantDataArray = response.data[responseDataKey];
        if (Array.isArray(applicantDataArray)) {
          setApplicantData(applicantDataArray);
        } else {
          setApplicantData([]);
        }
        setIsLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching applicant data:', error);
        setApplicantData([]);
        setIsLoading(false);
      });
  }, [circularId]);

  const sortedApplicantData = applicantData.sort((a, b) => {
    for (const field in sortOrder) {
      if (a[field] < b[field]) return sortOrder[field] === 'asc' ? -1 : 1;
      if (a[field] > b[field]) return sortOrder[field] === 'asc' ? 1 : -1;
    }
    return 0;
  });

  return (
    <div>
      <h2>Applicant Data for Circular ID: {circularId}</h2>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
          <thead>
            <tr style={{ borderBottom: '1px solid #ccc' }}>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('applicantionId')}
              >
                Applicantion ID
                {sortOrder.applicantId === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('firstName')}
              >
                First Name
                {sortOrder.firstName === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('lastName')}
              >
                Last Name
                {sortOrder.lastName === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('educationalInstitute')}
              >
                Educational Institute
                {sortOrder.educationalInstitute === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('degreeName')}
              >
                Degree Name
                {sortOrder.degreeName === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('cgpa')}
              >
                CGPA
                {sortOrder.cgpa === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('gender')}
              >
                Gender
                {sortOrder.gender === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th
                style={{ padding: '10px', textAlign: 'left', cursor: 'pointer' }}
                onClick={() => handleSorting('passingYear')}
              >
                Passing Year
                {sortOrder.passingYear === 'asc' ? ' ▲' : ' ▼'}
              </th>
              <th style={{ padding: '10px', textAlign: 'left' }}>Action</th>
            </tr>
          </thead>
          <tbody>
            {sortedApplicantData.map((applicant) => (
              <tr key={applicant.applicationId} style={{ borderBottom: '1px solid #ccc' }}>
                <td style={{ padding: '10px' }}>{applicant.applicationId}</td>
                <td style={{ padding: '10px' }}>{applicant.firstName}</td>
                <td style={{ padding: '10px' }}>{applicant.lastName}</td>
                <td style={{ padding: '10px' }}>{applicant.educationalInstitute}</td>
                <td style={{ padding: '10px' }}>{applicant.degreeName}</td>
                <td style={{ padding: '10px' }}>{applicant.cgpa}</td>
                <td style={{ padding: '10px' }}>{applicant.gender}</td>
                <td style={{ padding: '10px' }}>{applicant.passingYear}</td>
                <td style={{ padding: '10px' }}>
                  <button onClick={() => handleButtonClick(applicant.applicationId)}>
                    Approve
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ViewApplication;
