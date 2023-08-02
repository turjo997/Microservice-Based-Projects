import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import './Home.scss';

const Home = () => {
  const [showModal, setShowModal] = useState(false);

  const handleApplyClick = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <div className="home">
      <header className="header">
        <div className="header-content">
          <h1>Welcome to Youth Skill Development Hands on Training</h1>
          <p>We aim to find the best talents and train them to succeed.</p>
          <button className="apply-btn" onClick={handleApplyClick}>Apply Now</button>
        </div>
      </header>

      <section className="info-section">
        <div className="info-container">
          <div className="info-card">
            <h2>Why Choose Us?</h2>
            <p>
              We have a proven track record of discovering and nurturing
              exceptional talents who later become industry leaders.
            </p>
          </div>
          <div className="info-card">
            <h2>Our Programs</h2>
            <p>
              We offer comprehensive training programs in various fields,
              providing our trainees with the skills and knowledge needed to
              excel in their careers.
            </p>
          </div>
          <div className="info-card">
            <h2>Join Our Team</h2>
            <p>
              We are always looking for dedicated individuals who are passionate
              about learning and ready to take their careers to the next level.
            </p>
          </div>
        </div>
      </section>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Instruction Set</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ol>
            <li>Register as an applicant.</li>
            <li>Upload resources.</li>
            <li>Apply for training.</li>
          </ol>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>Close</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Home;
