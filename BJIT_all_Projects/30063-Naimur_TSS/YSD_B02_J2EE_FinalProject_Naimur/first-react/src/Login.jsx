import React, { useState, useEffect } from 'react';
import axios from 'axios';
import jwt_decode from 'jwt-decode';
import AdminDashboard from './AdminDashboard';
import UserDashboard from './UserDashBoard';
import Registration from './components/Registration';
import EvaluatorDashboard from './EvaluatorDashboard';
import base_url from './api/bootapi';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState('');
  const [showRegistration, setShowRegistration] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8084/user/login', {
        email,
        password,
      });
      const { token } = response.data;

      // Save the token to session storage or local storage for future requests
      localStorage.setItem('token', token);
      console.log(token);

      // Decode the JWT token to get the role and email
      const decodedToken = jwt_decode(token);
      const role = decodedToken.roles[0].authority;
      const userEmail = decodedToken.sub;
      console.log(role);
      console.log(userEmail);

      setUserRole(role);
      setLoggedIn(true);

      // Save the userEmail to localStorage for future use (optional)
      localStorage.setItem('userEmail', userEmail);
    } catch (error) {
      // Handle login error, display error message to the user
      setError('Invalid email or password');
    }
  };

  const handleRegister = () => {
    setShowRegistration(true);
  };

  useEffect(() => {
    const fetchApplicantOrEvaluatorId = async () => {
      if (loggedIn) {
        try {
          if (userRole === 'USER') {
            // Fetch the applicantId
            const applicantResponse = await axios.get(
              `${base_url}/applicant/${localStorage.getItem('userEmail')}`,
              {
                headers: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
              }
            );
            const applicantId = applicantResponse.data;
            localStorage.setItem('applicantId', String(applicantId));
            console.log(applicantId);
          } else if (userRole === 'EVALUATOR') {
            // Fetch the evaluatorId
            const evaluatorResponse = await axios.get(
              `${base_url}/evaluator/${localStorage.getItem('userEmail')}`,
              {
                headers: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
              }
            );
            const evaluatorId = evaluatorResponse.data;
            localStorage.setItem('evaluatorId', String(evaluatorId));
            console.log(evaluatorId);
          }
        } catch (error) {
          console.error('Error fetching applicantId or evaluatorId:', error);
        }
      }
    };
  
    fetchApplicantOrEvaluatorId();
  }, [loggedIn, userRole]);
  

  // Retrieve the applicantId from localStorage, if it exists
  const applicantIdString = localStorage.getItem('applicantId');
  const applicantId = parseInt(applicantIdString, 10);

  if (loggedIn) {
    if (userRole === 'ADMIN') {
      return <AdminDashboard />;
    } else if (userRole === 'USER') {
      return <UserDashboard />;
    }
    else if (userRole === 'EVALUATOR') {
      return <EvaluatorDashboard/>;
    }
  }

  if (showRegistration) {
    return <Registration />;
  }

  return (
    <div className="login-container">
      <div className="login-box">
        <h1>Login</h1>
        <form className="login-form" onSubmit={handleLogin}>
          <input
            type="text"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {error && <p className="error-message">{error}</p>}
          <button type="submit">Login</button>
        </form>
        <div className="register-container">
          <p className="register-text">Don't have an account?</p>
          <button className="register-button" onClick={handleRegister}>
          Register
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
