import React from 'react';
import {  Avatar  } from '@mui/material';

const AvatarImage = ({id, size}) => {

  const imageSrc = "http://localhost:8085/api/file-download/image/"+id.toString();

  return (
    <Avatar sx={{ width: size, height: size }} alt="User" src={imageSrc} />

  );
};

export default AvatarImage;
