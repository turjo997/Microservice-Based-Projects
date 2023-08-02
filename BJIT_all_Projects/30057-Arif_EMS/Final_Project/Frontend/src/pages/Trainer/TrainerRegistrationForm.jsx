import { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import axios from 'axios';
import './TrainerRegistrationForm.css'; // Import custom CSS for the fancy design
import { Link } from 'react-router-dom';

const TrainerRegistrationForm = () => {
  const [trainer, setTrainer] = useState({
    designation: '',
    fieldOfExpertise: '',
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
    setTrainer((prevTrainer) => ({
      ...prevTrainer,
      [name]: value
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setTrainer((prevTrainer) => ({
      ...prevTrainer,
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
      !trainer.designation ||
      !trainer.fieldOfExpertise ||
      !trainer.email ||
      !trainer.password ||
      !trainer.fullName ||
      !trainer.address ||
      !trainer.image
    ) {
      setShowErrorMessage(true);
      return;
    }

    try {
      const formData = new FormData();
      formData.append('designation', trainer.designation);
      formData.append('fieldOfExpertise', trainer.fieldOfExpertise);
      formData.append('email', trainer.email);
      formData.append('password', trainer.password);
      formData.append('fullName', trainer.fullName);
      formData.append('address', trainer.address);
      formData.append('image', trainer.image);

      await axios.post('http://localhost:8080/user/register/trainer', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      setShowSuccessMessage(true);
      setTrainer({
        designation: '',
        fieldOfExpertise: '',
        email: '',
        password: '',
        fullName: '',
        address: '',
        image: null
      });
    } catch (error) {
      console.error('Error registering trainer:', error);
      setShowErrorMessage(true);
    }
  };

  return (
    <div className="trainer-registration-form-container">
      <Form onSubmit={handleSubmit}>
        <h4>Trainer Registration Form</h4>
        <Form.Group controlId="designation">
          <Form.Label>Designation</Form.Label>
          <Form.Control
            type="text"
            name="designation"
            value={trainer.designation}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="fieldOfExpertise">
          <Form.Label>Field of Expertise</Form.Label>
          <Form.Control
            type="text"
            name="fieldOfExpertise"
            value={trainer.fieldOfExpertise}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="email">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            value={trainer.email}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={trainer.password}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="fullName">
          <Form.Label>Full Name</Form.Label>
          <Form.Control
            type="text"
            name="fullName"
            value={trainer.fullName}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="address">
          <Form.Label>Address</Form.Label>
          <Form.Control
            as="textarea"
            name="address"
            value={trainer.address}
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

export default TrainerRegistrationForm;
