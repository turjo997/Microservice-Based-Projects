import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Notification.scss';

const NotificationProp = ({ applicantId }) => {
  const [applicantData, setApplicantData] = useState(null);
  const [selectedForWrittenTest, setSelectedForWrittenTest] = useState(false);
  const [passedWrittenTest, setPassedWrittenTest] = useState(false);
  const [resultGenerated, setResultGenerated] = useState(false);

  useEffect(() => {
    const fetchApplicantData = async () => {
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
    };

    const fetchAdmitCardData = async () => {
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
    };

    const fetchWrittenTestData = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`http://localhost:8080/written/applicantId/${applicantId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.data && response.data.mark > 17) {
          setPassedWrittenTest(true);
        } else {
          setPassedWrittenTest(false);
        }
      } catch (error) {
        console.error('Error fetching written test data:', error);
      }
    };

    const fetchResultData = async () => {
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
    };

    fetchApplicantData();
    fetchAdmitCardData();
    fetchWrittenTestData();
    fetchResultData();
  }, [applicantId]);

  return (
    <div className="notification-container">
      {applicantData && (
        <>
          <div className="notification-card">
            <h3>Registration Confirmed! 🌟</h3>
            <p>Applicant details:</p>
            <p>👤 Name: {applicantData.firstName} {applicantData.lastName}</p>
            <p>📧 Email: {applicantData.email}</p>
            <p>🎓 Educational Institute: {applicantData.educationalInstitute}</p>
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
            <div className="notification-card">
              <h3>Sorry</h3>
              <p>You are not selected for interview.</p>
            </div>
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
          <h3>No notification found for you. Perhape you have not applied for any circular.</h3>
        </div>
      )}
    </div>
  );
};

export default NotificationProp;
