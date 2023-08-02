import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Notification.scss';

const NotificationP = () => {
  const [applicantData, setApplicantData] = useState(null);
  const [selectedForWrittenTest, setSelectedForWrittenTest] = useState(false);
  const [passedWrittenTest, setPassedWrittenTest] = useState(false);
  const [resultGenerated, setResultGenerated] = useState(false);
  const [applicantIdFromUser, setApplicantIdFromUser] = useState('');

  useEffect(() => {
    // Function to decode applicantId from the user input
    const decodeApplicantId = () => {
      try {
        const applicantId = atob(applicantIdFromUser);
        return applicantId;
      } catch (error) {
        console.error('Error decoding applicantId:', error);
        return null;
      }
    };

    const fetchApplicantData = async () => {
      const applicantId = decodeApplicantId();
      if (applicantId) {
        try {
          const token = localStorage.getItem('token');
          const response = await axios.get(`http://localhost:8080/applicant/${applicantId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          setApplicantData(response.data);
        } catch (error) {
          console.error('Error fetching applicant data:', error);
        }
      }
    };

    const fetchAdmitCardData = async () => {
      const applicantId = decodeApplicantId();
      if (applicantId) {
        try {
          const token = localStorage.getItem('token');
          const response = await axios.get(`http://localhost:8080/admitcard/${applicantId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          setSelectedForWrittenTest(!!response.data);
        } catch (error) {
          console.error('Error fetching admit card data:', error);
        }
      }
    };

    const fetchWrittenTestData = async () => {
      const applicantId = decodeApplicantId();
      if (applicantId) {
        try {
          const token = localStorage.getItem('token');
          const response = await axios.get(`http://localhost:8080/written/applicantId/${applicantId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          if (response.data && response.data.mark > 5) {
            setPassedWrittenTest(true);
          } else {
            setPassedWrittenTest(false);
          }
        } catch (error) {
          console.error('Error fetching written test data:', error);
        }
      }
    };

    const fetchResultData = async () => {
      const applicantId = decodeApplicantId();
      if (applicantId) {
        try {
          const token = localStorage.getItem('token');
          const response = await axios.get(`http://localhost:8080/result/applicant/${applicantId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          setResultGenerated(!!response.data && response.data.length > 0);
        } catch (error) {
          console.error('Error fetching result data:', error);
        }
      }
    };

    fetchApplicantData();
    fetchAdmitCardData();
    fetchWrittenTestData();
    fetchResultData();
  }, [applicantIdFromUser]);

  const handleFormSubmit = (event) => {
    event.preventDefault();
    const applicantIdInput = event.target.applicantId.value;
    setApplicantIdFromUser(applicantIdInput);
  };

  return (
    <div className="notification-container">
      <form onSubmit={handleFormSubmit} style={{ textAlign: 'center', margin: '20px auto', maxWidth: '300px', padding: '10px', border: '1px solid #ccc', borderRadius: '5px' }}>
        <label style={{ display: 'block', marginBottom: '10px' }}>
          Enter your Temporary ID:
          <input type="text" name="applicantId" required style={{ marginLeft: '5px', padding: '5px', borderRadius: '3px', border: '1px solid #ccc', fontSize: '16px', width: '100%', boxSizing: 'border-box' }} />
        </label>
        <button type="submit" style={{ padding: '10px 15px', fontSize: '16px', backgroundColor: '#007BFF', color: '#fff', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>Submit</button>
      </form>




      {applicantData && (
        <>
          <div className="notification-card">
            <h3>Registration Confirmed! 🌟</h3>
            <p>Applicant details:</p>
            <p>👤 Name: {applicantData.firstName} {applicantData.lastName}</p>
            <p>📧 Email: {applicantData.email}</p>
            <p>🎓 Educational Institute: {applicantData.educationalInstitute}</p>
          </div>
          <div>

          </div>
          {selectedForWrittenTest && (
            <div className="notification-card">
              <h3>Admit Card Published!</h3>
              <p>Exciting news! 🎉 Your admit card is ready, and we're glad to inform you that you have been selected for the upcoming written test. 📝👍 Congratulations, and best of luck for the test! 🍀🚀</p>

            </div>
          )}
          {selectedForWrittenTest && passedWrittenTest ? (
            <div className="notification-card">
              <h3>Interview Round!</h3>
              <p>Congratulations! 🎉 You've successfully passed the written test and now have the chance to be selected for the next round of interviews. 📝🤞 Best of luck! 🍀🚀</p>
            </div>
          ) : selectedForWrittenTest ? (
            <div></div>
            // <div className="notification-card">
            //   <h3>Sorry</h3>
            //   <p>You are not selected for interview.</p>
            // </div>
          ) : null}
          {resultGenerated && (
            <div className="notification-card">
              <h3>Score Generated!</h3>
              <p>Great news! 🎉 Your results are out, and we'll be in touch shortly to discuss your selection for training. 📚🚀</p>
            </div>
          )}
        </>
      )}
      {!applicantData && (
        <div className="notification-card">
          <h3>No notification found for you. Perhaps you have not applied for any circular.</h3>
        </div>
      )}

    </div>
  );
};

export default NotificationP;
