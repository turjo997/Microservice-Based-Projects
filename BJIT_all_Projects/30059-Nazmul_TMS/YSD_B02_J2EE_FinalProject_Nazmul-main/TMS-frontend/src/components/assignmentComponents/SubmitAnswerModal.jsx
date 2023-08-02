import React,{useState} from 'react';
import './AssignmentModal.css';
import ReactModal from 'react-modal';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { useDispatch, useSelector } from 'react-redux';
import { submitAssignmentAnswer } from '../../redux/assignmentRedux/assignmentActions';

const SubmitAnswerModal = ({ assignmentId, closeModal, isModalOpen  }) => {
  // console.log('assignment id ', assignment?.id);
  console.log("in submit modal:",assignmentId);
  if (!assignmentId) return null; // Handle case if assignment is not selected or doesn't exist
  
  const [selectedFile, setSelectedFile] = useState(null);
  const dispatch = useDispatch();
  const successMessage = useSelector((state)=>state.assignment.successMessage);
  const error = useSelector((state)=>state.assignment.error);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);
  };

  const handleSubmitAnswer = (e) =>{
    e.preventDefault();
    const formData = new FormData();
    formData.append("assignmentId", assignmentId);
    if (selectedFile) {
        formData.append('answerFile', selectedFile);
    }
    dispatch(submitAssignmentAnswer(formData));
    setSelectedFile('');
  }

  

  return (
    <>
    <ReactModal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        contentLabel="Update Assignment Modal"
      className="modal-content"
      overlayClassName="modal-overlay"
      >
        <h2>Submit Answer to this assignment</h2>
        <button className="close-button" onClick={closeModal}>
        <FontAwesomeIcon icon={faTimes} />
      </button>
      <form className="assignment-form" onSubmit={handleSubmitAnswer}>
        <div>
          <label>ID:</label>
          <input type="text" value={assignmentId} readOnly />
        </div>
        <div>
          <label>File Name:</label>
          <div className="file-input-container">
            <label className="custom-file-input">
              Browse
              <input type="file" onChange={handleFileChange} />
            </label>
          </div>
        </div>
        <button type="submit" style={{display:'flex', justifyContent:'center',marginLeft:'20px'}}>Submit Answer</button>
      </form>
      {successMessage && (<p style={{color:'green'}}>{successMessage}</p>)}
      {error && <p style={{color:'red'}}>{error}</p>}
      </ReactModal>
    </>
  );
};

export default SubmitAnswerModal;
