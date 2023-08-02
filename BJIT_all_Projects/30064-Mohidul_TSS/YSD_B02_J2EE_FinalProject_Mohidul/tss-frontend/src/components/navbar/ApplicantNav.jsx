import React from 'react';
import './ApplicantNav.scss';
import { Navbar, Container, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const ApplicantNav = () => {
  return (
    <Navbar bg="dark" data-bs-theme="dark">
      <Container>
        <Navbar.Brand href="/">Applicant</Navbar.Brand>
        <Nav className="me-auto">
          <Link to="/applicant-register" className="nav-link">Applicant Registration</Link>          
          <Link to="/apply" className="nav-link">Apply For Training</Link>
          <Link to="/notification" className="nav-link">Notification</Link>
          <Link to="/admitcard" className="nav-link">Get Admitcard</Link>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default ApplicantNav;
