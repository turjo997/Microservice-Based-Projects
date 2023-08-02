import React, { useState } from 'react';
import './UserFormModal.css'

const UserFormModal = ({ isOpen, onClose }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [gender, setGender] = useState('');
  const [phoneNo, setPhoneNo] = useState('');
  const [role, setRole] = useState('');

  const token = localStorage.getItem('token');
  const handleFormSubmit = async(e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);
    formData.append('firstName', firstName);
    formData.append('lastName', lastName);
    if(selectedFile){
      formData.append('profilePicture', selectedFile);
    }
    formData.append('gender', gender);
    formData.append('phoneNo', phoneNo);
    formData.append('role', role);
    try{
      const response = await fetch('http://localhost:8081/api/user/registerUser',{
        method:'POST',
        headers: {
          Authorization: `${token}`,
        },
        body: formData,
      });
      if(response.ok){
        const data = await response.json();
        console.log(data);
      } else{
        const data = await response.text();
        console.log(data);
      }
    }catch(error){
      console.log("AN error occured");
    }
    // Clear form fields after submission
    setEmail('');
    setPassword('');
    setFirstName('');
    setLastName('');
    setSelectedFile(null);
    setGender('');
    setPhoneNo('');
    setRole('');

    // Close the modal form
    onClose();
  };

  const handleFileInputChange = (e) => {
    const file = e.target.files[0];
    setSelectedFile(file);
  };

  return (
    <div className={`modal ${isOpen ? 'open' : ''}`}>
      <div className="modal-content">
        <span className="close" onClick={onClose}>
          &times;
        </span>
        <h2>Trainee Registration Form</h2>
        <form onSubmit={handleFormSubmit} enctype="multipart/form-data">
          <div>
            <label>Email:</label>
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div>
            <label>Password:</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          </div>
          <div>
            <label>First Name:</label>
            <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
          </div>
          <div>
            <label>Last Name:</label>
            <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
          </div>
          <div>
            <label>Profile Image:</label>
            <input type="file" onChange={handleFileInputChange} accept="image/*" />
          </div>
          <div>
            <label>Gender:</label>
            <select value={gender} onChange={(e) => setGender(e.target.value)} required>
              <option value="">Select Gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
              <option value="other">Other</option>
            </select>
          </div>
          <div>
            <label>Phone No:</label>
            <input type="tel" value={phoneNo} onChange={(e) => setPhoneNo(e.target.value)} required />
          </div>
          <div>
            <label>Role:</label>
            <select value={role} onChange={(e) => setRole(e.target.value)} required>
              <option value="">Select Role</option>
              <option value="trainee">TRAINEE</option>
              <option value="trainer">TRAINER</option>
            </select>
          </div>
          <button type="submit">Submit</button>
        </form>
      </div>
    </div>
  );
};

export default UserFormModal;
