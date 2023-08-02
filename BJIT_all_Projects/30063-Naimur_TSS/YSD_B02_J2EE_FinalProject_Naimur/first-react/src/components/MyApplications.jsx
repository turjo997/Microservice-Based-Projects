import React, { useEffect, useState } from 'react';
import axios from 'axios';

const MyApplications = () => {
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    
    const applicantId = localStorage.getItem('applicantId');
    const token = localStorage.getItem('token');

 
    axios
      .get(`http://localhost:8084/applicant/applications/${applicantId}`, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      })
      .then((response) => {
        setApplications(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);


  const handleDownloadAdmitCard = (applicantId, approvalStatus) => {
    const token = localStorage.getItem('token');
  
    if (approvalStatus) {
     
      axios({
        url: `http://localhost:8084/applicant/admit-card/${applicantId}`,
        method: 'GET',
        responseType: 'blob',
        headers: {
          Authorization: `Bearer ${token}` 
        }
      })
        .then((response) => {
          
          const file = new Blob([response.data], { type: 'application/pdf' });

         
          const fileURL = URL.createObjectURL(file);
          const link = document.createElement('a');
          link.href = fileURL;
          link.setAttribute('download', `admit_card_${applicantId}.pdf`);
          document.body.appendChild(link);

          
          link.click();

          
          URL.revokeObjectURL(fileURL);
          document.body.removeChild(link);
        })
        .catch((error) => {
          console.error('Error downloading admit card:', error);
        });
    } else {
      console.log('Cannot download admit card for unapproved application.');
    }
  };

  return (
    <div>
      <h2>My Applications</h2>
      <ul>
        {applications.map((application) => (
          <li key={application.applicationId}>
            <strong>Application ID:</strong> {application.applicationId} <br />
            <strong>Applicant ID:</strong> {application.applicantEntity.applicantId} <br />
            <strong>First Name:</strong> {application.applicantEntity.firstName} <br />
            <strong>Last Name:</strong> {application.applicantEntity.lastName} <br />
            <strong>Circular Name:</strong> {application.circular.circularName} <br />
            <strong>Approval Status:</strong> {application.approvalStatus ? 'Approved' : 'Pending'} <br />
            {/* Pass the approvalStatus to the handleDownloadAdmitCard function */}
            <button onClick={() => handleDownloadAdmitCard(application.applicantEntity.applicantId, application.approvalStatus)}>
              Download Admit Card
            </button>
            <hr />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default MyApplications;
