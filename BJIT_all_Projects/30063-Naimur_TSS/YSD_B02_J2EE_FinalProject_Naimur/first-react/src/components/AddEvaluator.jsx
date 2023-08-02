import React, { useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

const AddEvaluatorForm = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    gender: '',
    dob: '',
    email: '',
    contactNumber: '',
    degreeName: '',
    educationalInstitute: '',
    cgpa: '',
    passingYear: '',
    address: '',
    userId: ''
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const token = localStorage.getItem('token');
    axios.post('http://localhost:8084/admin/evaluator/register', formData, {
      headers: {
        Authorization: `Bearer ${token}`, 
        'Content-Type': 'application/json',
      }
    })
      .then(response => {
        console.log('Evaluator registered successfully:', response.data);
        toast.success("Evaluator registered successfully");
        
      })
      .catch(error => {
        console.error('Error adding evaluator:', error);
      });
  };

  return (
    <div>
      <h1>Add Evaluator</h1>
      <form onSubmit={handleSubmit}>
        <label>
          First Name:
          <input type="text" name="firstName" value={formData.firstName} onChange={handleChange} />
        </label>
        <label>
          Last Name:
          <input type="text" name="lastName" value={formData.lastName} onChange={handleChange} />
        </label>
        <label>
          Gender:
          <input type="text" name="gender" value={formData.gender} onChange={handleChange} />
        </label>
        <label>
          Date of Birth:
          <input type="text" name="dob" value={formData.dob} onChange={handleChange} />
        </label>
        <label>
          Email:
          <input type="text" name="email" value={formData.email} onChange={handleChange} />
        </label>
        <label>
          Contact Number:
          <input type="text" name="contactNumber" value={formData.contactNumber} onChange={handleChange} />
        </label>
        <label>
          Degree Name:
          <input type="text" name="degreeName" value={formData.degreeName} onChange={handleChange} />
        </label>
        <label>
          Educational Institute:
          <input type="text" name="educationalInstitute" value={formData.educationalInstitute} onChange={handleChange} />
        </label>
        
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default AddEvaluatorForm;
