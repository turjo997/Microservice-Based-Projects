// ApplyPage.jsx
import React from 'react';
import { useParams } from 'react-router-dom';

const ApplyPage = () => {
  const { circularId } = useParams();

  return (
    <div>
      <h2>Apply for Circular ID: {circularId}</h2>
      {/* You can add the apply form or any other content for the "Apply" page here */}
    </div>
  );
};

export default ApplyPage;
