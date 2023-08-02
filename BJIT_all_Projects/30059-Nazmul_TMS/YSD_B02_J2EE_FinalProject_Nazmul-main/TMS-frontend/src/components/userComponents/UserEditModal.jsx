// ProfileEditForm.js
import React, { useState } from "react";
import './UserEditModal.css';
import { useDispatch } from "react-redux";
import { updatProfile } from "../../redux/userRedux/userActions";

const UserEditModal = ({ profile, role, onCancel }) => {
  const [editedProfile, setEditedProfile] = useState(profile);
  const [selectedFile, setSelectedFile] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedProfile({ ...editedProfile, [name]: value });
  };

  const dispatch = useDispatch();

  const handleFileInputChange = (e) => {
    const file = e.target.files[0];
    setSelectedFile(file);
  };


  const handleSubmit = (e)=> {
    e.preventDefault();
    // Create a new FormData object
    const formData = new FormData();

    // Append the form fields to the FormData object
    formData.append('id', editedProfile.id);
    formData.append('email', editedProfile.email);
    formData.append('fullName', editedProfile.fullName);
    formData.append('bloodGroup', editedProfile.bloodGroup);
    formData.append('cgpa', editedProfile.cgpa);
    formData.append('passingYear', editedProfile.passingYear);
    formData.append('institute', editedProfile.institute);
    formData.append('phoneNo', editedProfile.phoneNo);
    formData.append('nId', editedProfile.nId);
    if(selectedFile){
      formData.append('imageFile', selectedFile);
    }
    formData.append('designation', editedProfile.designation);
    formData.append('experience', editedProfile.experience);
    formData.append('expertise', editedProfile.expertise);
    
    dispatch(updatProfile(formData));
  }
  return (
    <>
    {role === "TRAINEE" && (
      <div className="edit-form-container">
      <h2>Edit Profile</h2>
      <form className="edit-form" onSubmit={handleSubmit}>
      <div className="form-group">
          <label>ID:</label>
          <input type="id" name="id" value={editedProfile.id} readOnly />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input type="email" name="email" value={editedProfile.email} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Full Name:</label>
          <input type="text" name="fullName" value={editedProfile.fullName} onChange={handleChange}/>
        </div>
        <div className="form-group">
          <label>Blood group:</label>
          <input type="text" name="bloodGroup" value={editedProfile.bloodGroup} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>CGPA:</label>
          <input type="text" name="cgpa" value={editedProfile.cgpa} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Passing Year:</label>
          <input type="text" name="passingYear" value={editedProfile.passingYear} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Institute:</label>
          <input type="text" name="institute" value={editedProfile.institute} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Phone No:</label>
          <input type="text" name="phoneNo" value={editedProfile.phoneNo} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>National Id:</label>
          <input type="text" name="nId" value={editedProfile.nId} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Profile Picture:</label>
          <input type="file" onChange={handleFileInputChange} />
          {editedProfile.profilePicture && (
            <img src={`http://localhost:8081/api/user/images/${editedProfile.profilePicture}`} alt="Profile" className="preview-profile-picture" />
          )}
        </div>
        <div className="form-actions">
          <button type="submit">
            Save
          </button>
          <button type="button" onClick={onCancel}>
            Cancel
          </button>
        </div>
        
      </form>
    </div>
    )}
    {role==="TRAINER" && (
      <div className="edit-form-container">
      <h2>Edit Profile</h2>
      <form className="edit-form" onSubmit={handleSubmit}>
      <div className="form-group">
          <label>ID:</label>
          <input type="id" name="id" value={editedProfile.id} readOnly />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input type="email" name="email" value={editedProfile.email} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Full Name:</label>
          <input type="text" name="fullName" value={editedProfile.fullName} onChange={handleChange}/>
        </div>
        <div className="form-group">
          <label>Designation:</label>
          <input type="text" name="designation" value={editedProfile.designation} onChange={handleChange}/>
        </div>
        <div className="form-group">
          <label>Experience:</label>
          <input type="text" name="experience" value={editedProfile.experience} onChange={handleChange}/>
        </div>
        <div className="form-group">
          <label>Expertise:</label>
          <input type="text" name="expertise" value={editedProfile.expertise} onChange={handleChange}/>
        </div>

        <div className="form-group">
          <label>Phone No:</label>
          <input type="text" name="phoneNo" value={editedProfile.phoneNo} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Profile Picture:</label>
          <input type="file" onChange={handleFileInputChange} />
          {editedProfile.profilePicture && (
            <img src={`http://localhost:8081/api/user/images/${editedProfile.profilePicture}`} alt="Profile" className="preview-profile-picture" />
          )}
        </div>
        <div className="form-actions">
          <button type="submit">
            Save
          </button>
          <button type="button" onClick={onCancel}>
            Cancel
          </button>
        </div>
        </form>
        </div>
    )}
    </>
    )
    
};

export default UserEditModal;
