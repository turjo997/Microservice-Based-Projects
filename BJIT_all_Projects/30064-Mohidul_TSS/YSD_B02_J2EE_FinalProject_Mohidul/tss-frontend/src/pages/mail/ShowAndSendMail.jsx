import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ShowAndSendMail.scss';

const ShowAndSendMail = () => {
  const [mails, setMails] = useState([]);
  const [selectedMailId, setSelectedMailId] = useState(null);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/mail/', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setMails(response.data);
      })
      .catch((error) => {
        console.error('Error fetching mails:', error);
      });
  }, []);

  const handleSendToApproved = (mailId) => {
    setSelectedMailId(mailId);
    setShowForm(true);
  };

  const handleSendData = () => {
    if (username.trim() === '' || password.trim() === '') {
      console.error('Username and Password cannot be blank.');
      return;
    }

    const jsonData = {
      username,
      password,
    };

    const token = localStorage.getItem('token');
    axios.post(`http://localhost:8080/mail/sendToApplicants/${selectedMailId}`, jsonData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        console.log('Data sent successfully:', response.data);
      })
      .catch((error) => {
        console.error('Error sending data:', error);
      });

    setShowForm(false);
    setSelectedMailId(null);
    setUsername('');
    setPassword('');
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setSelectedMailId(null);
    setUsername('');
    setPassword('');
  };

  return (
    <div className="mail-list">
      {mails.map((mail) => (
        <div className="mail-card" key={mail.mailId}>
          <h3>{mail.subject}</h3>
          <p>{mail.body}</p>
          <p className="timestamp">{new Date(mail.timestamp).toLocaleString()}</p>
          <button onClick={() => handleSendToApproved(mail.mailId)}>Send to Approved</button>
        </div>
      ))}
      {showForm && (
        <div className="form">
          <h3>Provide Admin/Sender Email</h3>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button onClick={handleSendData}>Send</button>
          <button className="close-button" onClick={handleCloseForm}>X</button>
        </div>
      )}
    </div>
  );
};

export default ShowAndSendMail;
