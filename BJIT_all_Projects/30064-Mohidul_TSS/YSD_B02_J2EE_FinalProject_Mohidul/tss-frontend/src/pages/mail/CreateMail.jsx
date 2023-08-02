import React, { useState } from 'react';
import axios from 'axios';
import './CreateMail.scss';

const CreateMail = () => {
  const [subject, setSubject] = useState('');
  const [body, setBody] = useState('');
  const [showAlert, setShowAlert] = useState(false);

  const handleSaveToServer = () => {
    if (!subject || !body) {
      alert('Please enter both subject and body before saving.');
      return;
    }

    const jsonData = {
      subject,
      body,
    };

    const token = localStorage.getItem('token');
    axios.post('http://localhost:8080/mail/', jsonData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        console.log('Mail saved successfully:', response.data);
        setShowAlert(true);
        setSubject('');
        setBody('');
      })
      .catch((error) => {
        console.error('Error saving mail:', error);
      });
  };

  const handleCloseForm = () => {
    setShowAlert(false);
  };

  return (
    <div className="create-mail">
      <h2>Create Mail</h2>
      <div className="form">
        <input
          type="text"
          placeholder="Subject"
          value={subject}
          onChange={(e) => setSubject(e.target.value)}
        />
        <textarea
          placeholder="Body"
          value={body}
          onChange={(e) => setBody(e.target.value)}
        />
        <button onClick={handleSaveToServer}>Save to Server</button>

      </div>
      {showAlert && (
        <div className="alert">
          <span className="success-text">Mail saved successfully!</span>
          <button className="close-button" onClick={handleCloseForm}>
            &#10005;
          </button>
        </div>
      )}
    </div>
  );
};

export default CreateMail;
