import React from 'react';
import { Navbar, Container, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const EvaluatorNav = () => {
  return (
    <Navbar bg="dark" data-bs-theme="dark">
      <Container>
        <Navbar.Brand href="/">Evaluator</Navbar.Brand>
        <Nav className="me-auto">
          <Link to="/written-mark-upoad" className="nav-link">Mark Upload</Link>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default EvaluatorNav;
