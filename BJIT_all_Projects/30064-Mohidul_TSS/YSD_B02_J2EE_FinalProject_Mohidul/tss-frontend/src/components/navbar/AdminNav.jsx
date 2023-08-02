
import React from 'react';
import { Link } from 'react-router-dom';
import { Navbar, Container, Nav } from 'react-bootstrap';


const AdminNav = () => {
    return (
      <Navbar bg="dark" data-bs-theme="dark">
        <Container>
        <Navbar.Brand href="/">Admin</Navbar.Brand>
          <Nav className="me-auto">
            
            <Link to="/create-circular" className="nav-link">Launch training</Link>
            <Link to="/circular-show" className="nav-link">Circulars</Link>
            <Link to="/show-all-applicant" className="nav-link">All applicant</Link>
            <Link to="/approve" className="nav-link">Approve</Link>
            <Link to="/create-mail" className="nav-link">Store default mail</Link>
            <Link to="/show-send-mail" className="nav-link">Send Mail</Link>
            <Link to="/written-mark" className="nav-link">Written marks</Link>
            <Link to="/create-exam" className="nav-link">Exam</Link>
            <Link to="/upload-mark" className="nav-link">Mark Upload</Link>
            <Link to="/result" className="nav-link">Result</Link>
          </Nav>
        </Container>
      </Navbar>
    );
  };
export default AdminNav;
