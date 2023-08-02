import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import axios from 'axios';
import './AdminRegistrationForm.css';
import { Link } from 'react-router-dom';

const adminRegistrationForm = () => {
  const [admin, setadmin] = useState({
    designation: '',
    yearOfExperience: '',
    email: '',
    password: '',
    fullName: '',
    address: '',
    image: null
  });

  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setadmin((prevadmin) => ({
      ...prevadmin,
      [name]: value
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setadmin((prevadmin) => ({
      ...prevadmin,
      image: file
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    // Reset messages
    setShowSuccessMessage(false);
    setShowErrorMessage(false);
  
    // Form validation
    if (
      !admin.designation ||
      !admin.yearOfExperience ||
      !admin.email ||
      !admin.password ||
      !admin.fullName ||
      !admin.address ||
      !admin.image
    ) {
      setShowErrorMessage(true);
      return;
    }
  
    try {
      const formData = new FormData();
      formData.append('designation', admin.designation);
      formData.append('yearOfExperience', admin.yearOfExperience);
      formData.append('email', admin.email);
      formData.append('password', admin.password);
      formData.append('fullName', admin.fullName);
      formData.append('address', admin.address);
      formData.append('image', admin.image);
  
      await axios.post('http://localhost:8080/user/register/admin', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
  
      setShowSuccessMessage(true);
      setadmin({
        designation: '',
        yearOfExperience: '',
        email: '',
        password: '',
        fullName: '',
        address: '',
        image: null
      });
    } catch (error) {
      console.error('Error registering admin:', error);
      setShowErrorMessage(true);
    }
  };

  return (
    <div className="admin-registration-form-container adminForm">
      <Form onSubmit={handleSubmit}>
        <h4>Admin Registration Form</h4>
        <Form.Group controlId="designation">
          <Form.Label>Designation</Form.Label>
          <Form.Control
            type="text"
            name="designation"
            value={admin.designation}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="yearOfExperience">
          <Form.Label>Year of Experience</Form.Label>
          <Form.Control
            type="number"
            name="yearOfExperience"
            value={admin.yearOfExperience}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="email">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            value={admin.email}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={admin.password}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="fullName">
          <Form.Label>Full Name</Form.Label>
          <Form.Control
            type="text"
            name="fullName"
            value={admin.fullName}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="address">
          <Form.Label>Address</Form.Label>
          <Form.Control
            as="textarea"
            name="address"
            value={admin.address}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="image">
          <Form.Label>Profile Picture</Form.Label>
          <Form.Control
            type="file"
            name="image"
            onChange={handleFileChange}
            required
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Register
        </Button>

        {/* Error message */}

      
        
      </Form>
      <div className="login-link-container">
        <p>Already have an account? </p>
        <Link to="/">Login</Link>
      </div>
    </div>
  );
};

export default adminRegistrationForm;
