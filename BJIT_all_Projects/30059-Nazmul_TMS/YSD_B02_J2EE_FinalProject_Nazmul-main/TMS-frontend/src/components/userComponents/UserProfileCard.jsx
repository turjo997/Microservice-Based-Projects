// src/components/ProfileCard.js
import React from "react";
import './UserProfileCard.css';
import './images/default_profile-icon.jpg';

const UserProfileCard = ({ profile, role, onEdit }) => {
  const {
    fullName,
    email,
    bloodGroup,
    cgpa,
    passingYear,
    institute,
    nid,
    designation,
    experience,
    expertise,
    phoneNo,
    profilePicture,
  } = profile;

  const hasProfilePicture = profilePicture && profilePicture.trim() !== '';
  const defaultProfileIconUrl = 'default_profile-icon.jpg';

  return (
    <>
      {role === "TRAINER" && (
        <div className="profile-card" style={{ marginTop: '100px' }}>
          <div className="profile-picture-container">
        {hasProfilePicture ? (
          <img src={`http://localhost:8081/api/user/images/${profilePicture}`} alt="Profile" className="profile-picture" />
        ) : (
          <img src={defaultProfileIconUrl} alt="Default Profile" className="profile-picture" />
        )}
      </div>
          <div className="profile-details">
            <h2>{fullName}</h2>
            <table className="profile-table">
              <tbody>
                <tr>
                  <td style={{ marginLeft: '20px' }}><label>Email:</label></td>
                  <td><p>{email}</p></td>
                </tr>
                <tr>
                  <td><label>Designation:</label></td>
                  <td><p>{designation}</p></td>
                </tr>
                <tr>
                  <td><label>Experience:</label></td>
                  <td><p>{experience}</p></td>
                </tr>
                <tr>
                  <td><label>Expertise:</label></td>
                  <td><p>{expertise}</p></td>
                </tr>
                <tr>
                  <td><label>Phone:</label></td>
                  <td><p>{phoneNo}</p></td>
                </tr>
              </tbody>
            </table>
            <button onClick={onEdit}>Edit Profile</button>
          </div>
        </div>
      )}
      {role === "TRAINEE" && (
        <div className="profile-card" style={{ marginTop: '100px' }}>
          <div className="profile-picture-container">
            <img src={`http://localhost:8081/api/user/images/${profilePicture}`} alt="Profile" className="profile-picture" />
          </div>
          <div className="profile-details">
            <h2>{fullName}</h2>
            <table className="profile-table">
              <tbody>
                <tr>
                  <td style={{ marginLeft: '20px' }}><label>Email:</label></td>
                  <td><p>{email}</p></td>
                </tr>
                <tr>
                  <td><label>Blood Group:</label></td>
                  <td><p>{bloodGroup}</p></td>
                </tr>
                <tr>
                  <td><label>CGPA:</label></td>
                  <td><p>{cgpa}</p></td>
                </tr>
                <tr>
                  <td><label>Passing Year:</label></td>
                  <td><p>{passingYear}</p></td>
                </tr>
                <tr>
                  <td><label>Institute:</label></td>
                  <td><p>{institute}</p></td>
                </tr>
                <tr>
                  <td><label>National ID:</label></td>
                  <td><p>{nid}</p></td>
                </tr>
                <tr>
                  <td><label>Phone:</label></td>
                  <td><p>{phoneNo}</p></td>
                </tr>
              </tbody>
            </table>
            <button onClick={onEdit}>Edit Profile</button>
          </div>
        </div>
      )}

    </>
  );
};

export default UserProfileCard;


