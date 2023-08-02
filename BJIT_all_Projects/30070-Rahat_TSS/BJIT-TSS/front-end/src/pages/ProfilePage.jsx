import React, { useContext, useEffect } from 'react';
import { Typography, Box, Avatar, Button } from '@mui/material';
import { LoginContext } from '../context/LoginContex';
import { useNavigate } from 'react-router-dom';
import AvatarImage from '../components/AvatarImage';
import axios from '../api/axios';

const ProfileComponent = () => {
  const navigate = useNavigate();
  const { userData, loggedIn, role } = useContext(LoginContext);
 
  let imageSrc;

  if (role === 'APPLICANT') {
    
     imageSrc = "http://localhost:8085/api/file-download/image/" + userData.userId.toString();
  }


  useEffect(() => {
    if (!loggedIn) {
      navigate('/login');
    }
  }, [loggedIn, navigate]);

  if (!userData) {
    return <Typography variant="h6">Profile data not available</Typography>;
  }

  const handleResumeDownload = () => {

    const token = window.localStorage.getItem("tss-token");


    axios({
      url: '/api/file-download/resume', 
      method: 'GET',
      responseType: 'blob',
         headers: {
        Authorization: `Bearer ${token}`,
    },
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `resume_${userData.firstName}.pdf`); //or any other extension
      document.body.appendChild(link);
      link.click();


      // Add your logic to initiate the download of the resume
      // For example, you can use the 'userData.resumeUrl' to fetch the resume and trigger the download
    });
  }

  return (
    <Box mt={4} p={3} sx={{ background: '#f8f8f8', borderRadius: '8px', boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)' }}>
      <Typography variant="h4" mb={3}>
        Profile Information
      </Typography>

      {role === 'EVALUATOR' && (
        <Box>
          <Typography variant="subtitle1">Name: {userData.name || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Email: {userData.email || 'Not provided'}</Typography>
        </Box>
      )}

      {role === 'APPLICANT' && (
        <Box>
          <Box display="flex" alignItems="center" justifyContent="center" mb={2}>
            <Avatar sx={{ width: 100, height: 100 }} alt="User" src={imageSrc} />
          </Box>

          <Typography variant="subtitle1">Full Name: {`${userData.firstName || 'Not provided'} ${userData.lastName || 'Not provided'}`}</Typography>
          <Typography variant="subtitle1">Email: {userData.email || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Contact Number: {userData.contactNumber || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Date of Birth: {userData.dateOfBirth || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Gender: {userData.gender || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Degree Name: {userData.degreeName || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Educational Institute: {userData.educationalInstitute || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Passing Year: {userData.passingYear || 'Not provided'}</Typography>
          <Typography variant="subtitle1">CGPA: {userData.cgpa || 'Not provided'}</Typography>
          <Typography variant="subtitle1">Present Address: {userData.presentAddress || 'Not provided'}</Typography>


          <Box mt={2}>
            <Button variant="contained" color="primary" onClick={handleResumeDownload}>
              Download Resume
            </Button>
          </Box>
        </Box>
      )}
    </Box>
  );
};


export default ProfileComponent;
