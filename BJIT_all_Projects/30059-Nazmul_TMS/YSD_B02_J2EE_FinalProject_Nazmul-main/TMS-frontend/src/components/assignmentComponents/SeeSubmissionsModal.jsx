import React, { useState } from 'react';
import './AssignmentModal.css';
import ReactModal from 'react-modal';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

const SeeSubmissionsModal = ({ assignmentSubmissions, closeModal, isModalOpen }) => {
 
    console.log("SUBMISSIONS:", assignmentSubmissions);
 
    console.log("in modal:", isModalOpen);
  if (!assignmentSubmissions) return null; 

  
  return (
    <>
      <ReactModal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        contentLabel="Update Assignment Modal"
        className="modal-content"
        overlayClassName="modal-overlay"
      >
        <h2>See Submissions</h2>
        <button className="close-button" onClick={closeModal}>
          <FontAwesomeIcon icon={faTimes} />
        </button>
        <table className="assignment-table">
          <thead>
            <tr>
              <th>Assignment Title</th>
              <th>Answer ID</th>
              <th>Answer File</th>
              <th>Submitted By</th>
            </tr>
          </thead>
          <tbody>
            {assignmentSubmissions.map((a)=>(
                <tr key = {a.answerId}>
                <td>{a.assignmentTitle}</td>
                <td>{a.answerId}</td>
                <td>
                {a.answerFile && (
                  <a
                    href={`http://localhost:8081/api/assignment/files/${encodeURIComponent(a.answerFile)}`}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    {a.answerFile}
                  </a>
                )}
              </td>
                <td>{a.submittedBy}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="form-buttons">
          <button onClick={closeModal}>Cancel</button>
        </div>
      </ReactModal>
    </>
  );
};

export default SeeSubmissionsModal;
