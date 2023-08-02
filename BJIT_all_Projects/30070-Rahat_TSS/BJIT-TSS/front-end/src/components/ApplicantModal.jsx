import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import AvatarImage from './AvatarImage';

const CenteredModal = styled(Modal)(({ theme }) => ({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
}));

const ModalContent = styled(Box)(({ theme }) => ({
  backgroundColor: 'white',
  padding: theme.spacing(4),
  borderRadius: theme.spacing(2),
  textAlign: 'center',
}));

const ApplicantModal = ({ applicant, open, onClose }) => {
  return (
    <CenteredModal open={open} onClose={onClose}>
      <ModalContent>
        <Typography variant="h4" gutterBottom>
          Applicant Details
        </Typography>

        <Box display="flex" alignItems="center" justifyContent="center" mb={2}>

          <AvatarImage id={applicant.userInfo.userId} size={70} />
        </Box>

        <Typography variant="body1">
          Name : {applicant.userInfo.firstName}  {applicant.userInfo.lastName}
        </Typography>
       
        <Typography variant="body1">
          Educational Institute: {applicant.userInfo.educationalInstitute}
        </Typography>
        <Typography variant="body1">CGPA: {applicant.userInfo.cgpa}</Typography>

        <Typography variant="body1">
          Email : {applicant.userInfo.email}
        </Typography>
        <Typography variant="body1">
          Degree Name : {applicant.userInfo.degreeName}
        </Typography>
        <Typography variant="body1">
          Passing Year : {applicant.userInfo.passingYear}
        </Typography>

        <Typography variant="body1">
          Present Address : {applicant.userInfo.presentAddress}
        </Typography>




        <Button variant="contained" color="secondary" onClick={onClose}>
          Close
        </Button>
      </ModalContent>
    </CenteredModal>
  );
};

export default ApplicantModal;
