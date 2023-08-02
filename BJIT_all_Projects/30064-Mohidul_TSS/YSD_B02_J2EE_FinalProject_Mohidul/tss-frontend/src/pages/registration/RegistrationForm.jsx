import React, { useState } from 'react';
import './RegistrationForm.scss';
import axios from 'axios';

const RegistrationForm = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        role: 'APPLICANT',
    });

    const [message, setMessage] = useState('');

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({
            ...prevFormData,
            [name]: value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/user/register', formData);
            const token = response.data.token;
            window.localStorage.setItem('token', token);
            setMessage('Registration successful!');
            window.alert('Registration successful!');
            
        } catch (error) {
            setMessage('Registration failed! Please try again.');
            window.alert('Registration failed! Please try again.');            
        }
    };



    return (
        <div className="registration-form">
            <h2>SignUp</h2>
            {message && <div className={`message ${message.includes('successful') ? 'success' : 'error'}`}>{message}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Name</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit">Register</button>
            </form>
            
        </div>
    );
};

export default RegistrationForm;
