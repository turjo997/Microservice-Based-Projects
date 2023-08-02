import React,{useState} from 'react';
import './AssignmentModal.css';
import ReactModal from 'react-modal';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

const AssignmentModal = ({ assignment, closeModal, isModalOpen  }) => {
  // console.log('assignment id ', assignment?.id);
  console.log("in modal:",assignment);
  if (!assignment) return null; // Handle case if assignment is not selected or doesn't exist
  
  const [selectedFile, setSelectedFile] = useState(null);
  const [updatedTitle, setUpdatedTitle] = useState(assignment.assignmentTitle);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);
  };

  const handleTitleChange = (event) => {
    setUpdatedTitle(event.target.value);
  };

  const handleUpdateAssignment = async (event) => {
    const token = localStorage.getItem('token');
    event.preventDefault();

    const formData = new FormData();
    formData.append('id', assignment.id);
    formData.append('assignmentTitle', updatedTitle);
    if (selectedFile) {
      formData.append('file', selectedFile);
    }
    formData.append('endTime', assignment.endTime);

    try {
      const response = await fetch(`http://localhost:8081/api/assignment/update-assignment/${assignment.id}`, {
        method: 'PUT',
        headers:{
          Authorization:`${token}`,
        },
        body: formData,
      });

      if (response.ok) {
        console.log(await response.text());
        closeModal();
      } else {
        console.error('Failed to update assignment:', response.statusText);
        // Handle error
      }
    } catch (error) {
      console.error('Error updating assignment:', error);
      // Handle error
    }
  };

  return (
    <>
    <ReactModal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        contentLabel="Update Assignment Modal"
      className="modal-content"
      overlayClassName="modal-overlay"
      >
        <h2>Update Assignment</h2>
        <button className="close-button" onClick={closeModal}>
        <FontAwesomeIcon icon={faTimes} />
      </button>
      <form className="assignment-form" onSubmit={handleUpdateAssignment}>
        <div>
          <label>ID:</label>
          <input type="text" value={assignment.id} readOnly />
        </div>
        <div>
          <label>Assignment Title:</label>
          <input type="text" value={updatedTitle}
              onChange={handleTitleChange} />
        </div>
        <div>
          <label>File Name:</label>
          <div className="file-input-container">
            <span className="file-name">{assignment.fileName}</span>
            <label className="custom-file-input">
              Browse
              <input type="file" onChange={handleFileChange} />
            </label>
          </div>
        </div>
        <button type="submit" style={{display:'flex', justifyContent:'center',marginLeft:'20px'}}>Update</button>
      </form>
      </ReactModal>
    </>
  );
};

export default AssignmentModal;
