
import React, { useState } from 'react';
import './LoginForm.scss';
import axios from 'axios';

const LoginForm = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/user/login', formData);
      const token = response.data.token; 
      console.log('Login successful!', token);
      window.localStorage.setItem('token', token); 
      window.alert('Login successful!');
      window.location.reload();
      
    } catch (error) {
      console.error('Login failed!', error);
      window.alert('Login failed!');
    }
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  return (
    <div className="login-form">
      <h2>SignIn</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className='labelfield'>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label className='labelfield'>Password</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginForm;
