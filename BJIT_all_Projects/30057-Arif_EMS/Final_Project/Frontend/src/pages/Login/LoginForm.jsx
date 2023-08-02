import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './style.css'; // Import custom CSS for the login form

const LoginForm = () => {
  const [credentials, setCredentials] = useState({
    email: '',
    password: '',
  });

  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);


  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prevCredentials) => ({
      ...prevCredentials,
      [name]: value,
    }));
  };
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Reset messages
    setShowErrorMessage(false);
    setShowSuccessMessage(false);

    // Simple form validation for demonstration purposes
    if (!credentials.email || !credentials.password) {
      setShowErrorMessage(true);
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/user/login', credentials);
      const {role,name} = response.data;
      const userId = response.data.userId;
      const token = response.data.token;

      // Save the token to local storage
      localStorage.setItem('token', token);
      localStorage.setItem('role',role);
      localStorage.setItem('name',name);
      localStorage.setItem('userId',userId);
      console.log(userId);

      // Redirect to the home page
      navigate('/home');
      
    } catch (error) {
      console.error('Error logging in:', error);
      setShowErrorMessage(true);
    }
  };

  return (
    <div className="login-form-container">
      <Form onSubmit={handleSubmit} className="login-form">
        <h1>Login Form</h1>

        <Form.Group controlId="email">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            value={credentials.email}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={credentials.password}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Login
        </Button>

        {/* Error message */}
        {showErrorMessage && (
          <Alert variant="danger" className="mt-3">
            Please fill in both email and password fields.
          </Alert>
        )}

        {/* Success message */}
        {showSuccessMessage && (
          <Alert variant="success" className="mt-3">
            Login successful! Welcome, {credentials.email}.
          </Alert>
        )}

        <div className="login-link-container loginform">
          <p>New Here! Register as </p>
          <Link to="/admin">Admin</Link>
          <Link className="linklogin" to="/trainer">Trainer</Link>
          <Link to="/trainee">Trainee</Link>
        </div>
      </Form>
    </div>
  );
};

export default LoginForm;
