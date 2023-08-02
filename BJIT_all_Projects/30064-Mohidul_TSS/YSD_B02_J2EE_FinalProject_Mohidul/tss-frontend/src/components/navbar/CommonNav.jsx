import React from 'react';
import { Navbar, Container, Nav } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';

const CommonNav = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.reload();
    navigate('/login-form');
  };

  return (
    <Navbar bg="dark" data-bs-theme="dark">
      <Container>
        <Navbar.Brand href="/">Welcome!</Navbar.Brand>
        <Nav className="me-auto">
          <Link to="/" className="nav-link">Home</Link>
          <Link to="/registration-form" className="nav-link">Sign up</Link>
          <Link to="/login-form" className="nav-link">Login</Link>
        </Nav>
        <button className="btn btn-primary" onClick={handleLogout}>Logout</button>
      </Container>
    </Navbar>
  );
};

export default CommonNav;
