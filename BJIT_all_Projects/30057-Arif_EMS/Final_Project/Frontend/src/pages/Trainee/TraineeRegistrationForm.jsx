import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import axios from 'axios';
import './TraineeRegistrationForm.css'; // Import custom CSS for the fancy design
import { Link } from 'react-router-dom';

const TraineeRegistrationForm = () => {
  const [trainee, setTrainee] = useState({
    institute: '',
    cgpa: '',
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
    setTrainee((prevTrainee) => ({
      ...prevTrainee,
      [name]: value
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setTrainee((prevTrainee) => ({
      ...prevTrainee,
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
      !trainee.institute ||
      !trainee.cgpa ||
      !trainee.email ||
      !trainee.password ||
      !trainee.fullName ||
      !trainee.address ||
      !trainee.image
    ) {
      setShowErrorMessage(true);
      return;
    }
  
    try {
      const formData = new FormData();
      formData.append('institute', trainee.institute);
      formData.append('cgpa', trainee.cgpa);
      formData.append('email', trainee.email);
      formData.append('password', trainee.password);
      formData.append('fullName', trainee.fullName);
      formData.append('address', trainee.address);
      formData.append('image', trainee.image);
  
      await axios.post('http://localhost:8080/user/register/trainee', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
  
      setShowSuccessMessage(true);
      setTrainee({
        institute: '',
        cgpa: '',
        email: '',
        password: '',
        fullName: '',
        address: '',
        image: null
      });
    } catch (error) {
      console.error('Error registering trainee:', error);
      setShowErrorMessage(true);
    }
  };

  return (
    <div className="trainee-registration-form-container">
      <Form onSubmit={handleSubmit}>
        <h4>Trainee Registration Form</h4>
        <Form.Group controlId="institute">
          <Form.Label>Institute</Form.Label>
          <Form.Control
            type="text"
            name="institute"
            value={trainee.institute}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="cgpa">
          <Form.Label>CGPA</Form.Label>
          <Form.Control
            type="number"
            name="cgpa"
            value={trainee.cgpa}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="email">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            value={trainee.email}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={trainee.password}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="fullName">
          <Form.Label>Full Name</Form.Label>
          <Form.Control
            type="text"
            name="fullName"
            value={trainee.fullName}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="address">
          <Form.Label>Address</Form.Label>
          <Form.Control
            as="textarea"
            name="address"
            value={trainee.address}
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
        {/* {showErrorMessage && (
          <Alert variant="danger" className="mt-3">
            Please fill in all the required fields.
          </Alert>
        )} */}

        {/* Success message */}
        {/* {showSuccessMessage && (
          <Alert variant="success" className="mt-3">
            Registration successful! Your data has been submitted.
          </Alert>
        )} */}
        
      </Form>
      <div className="login-link-container">
        <p>Already have an account? </p>
        <Link to="/">Login</Link>
      </div>
    </div>
  );
};

export default TraineeRegistrationForm;
