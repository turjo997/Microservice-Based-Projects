import React from 'react';
import { Box } from '@mui/material';

import NotFoundIllustration from '../assets/not_found.svg';
import { useNavigate } from 'react-router-dom/dist/index';

const PageNotFound = () => {
    const navigate = useNavigate();

    setTimeout(() => {
     
        navigate("/");

      }, 4000);
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      height="70vh"
      padding={4}
      marginTop={2}
    >
      <img
        width="100%" // Set width to 100% for extra small screens
        src={NotFoundIllustration}
        alt="Page not found"
        style={{ alignItems: 'center', justifyContent: 'center', marginBottom: 8 }}
      />
    </Box>
  );
};

export default PageNotFound;
