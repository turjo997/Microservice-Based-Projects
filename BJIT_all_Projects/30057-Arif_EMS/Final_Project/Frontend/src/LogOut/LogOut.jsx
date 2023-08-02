import React, { useState, useEffect } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const LogOut = () => {
  const [showModal, setShowModal] = useState(true); // Set the initial state to true to show the modal immediately
  const navigate = useNavigate();

  useEffect(() => {
    // Hide the modal after 7 seconds (you can change the delay as needed)
    const timeout = setTimeout(() => {
      setShowModal(false);
    }, 7000);

    // Clear the timeout when the component is unmounted
    return () => {
      clearTimeout(timeout);
    };
  }, []);

  const handleLogout = () => {
    // Clear local storage
    localStorage.clear();
    // Redirect to login page
    navigate('/');
  };

  return (
    <Modal show={showModal} onHide={() => setShowModal(false)} centered backdrop="static" keyboard={false}>
      
      <p style={{ textAlign: 'center', fontWeight: 'bold' }}>Are you sure to Logout!</p>
        <Button variant="danger" onClick={handleLogout}>
          Confirm Logout
        </Button>
      
    </Modal>
  );
};

export default LogOut;

