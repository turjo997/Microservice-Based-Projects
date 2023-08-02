import React, { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import Select from "react-select";
import "./Card.css";
import defaultBatchImage from './images/academy.png';
import { useDispatch, useSelector } from "react-redux";
import { getAllTrainees, submitBatchTrainees } from "../../redux/batchRedux/batchActions";

const BatchCard = ({ batch }) => {
  const { batchId, batchName, startTime, endTime } = batch;

  const [showModal, setShowModal] = useState(false);
  const [selectedValues, setSelectedValues] = useState([]);
  const [items, setItems] = useState([]);
  const dispatch = useDispatch();
  const trainees = useSelector((state)=>state.batch.trainees);
  //const batchTrainees = useSelector((state)=>state.batch.batchTrainees);
  const successAssignMessage = useSelector((state)=>state.batch.successAssignMessage);
  useEffect(()=>{
    dispatch(getAllTrainees("TRAINEE"));
  },[]);

  const traineeOptions = trainees.map((trainee) => ({
    value: trainee.email,
    label: trainee.email,
  }));
  // const traineeOptions = trainees
  // .filter((trainee) => !selectedValues.includes(trainee.email)) // Exclude already selected trainees
  // .map((trainee) => ({
  //   value: trainee.email,
  //   label: trainee.email,
  // }));

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleSelectionChange = (selectedOptions) => {
    setSelectedValues(selectedOptions);
  };

  const handleSubmit = (e) => {
    // const traineeEmails = selectedValues.map((option) => option.value);
    e.preventDefault();
    // Handle form submission with selectedValues
    console.log("Selected Values:", selectedValues);
    
    const newItems = selectedValues.map((selectedValue) => ({
      batchName: batch.batchName,
      traineeEmail: selectedValue.value,
    }));
    setItems([...items, ...newItems]);
    console.log("new items",newItems);
    dispatch(submitBatchTrainees(newItems));
    setSelectedValues([]);
    window.location.href= '/batch/trainees';
    // handleCloseModal();
    
  };
    
  const imageUrl = batch.image || defaultBatchImage;
  return (
    <>
      <div className="batch-card">
        <div
          className="batch-card-image"
          style={{ backgroundImage: `url(${imageUrl})` }}
        />
        <div className="batch-card-content">
          <h3 className="batch-card-id">Batch ID: {batchId}</h3>
          <h2 className="batch-card-name">{batchName}</h2>
          <p className="batch-card-time">
            Start Time: {startTime} - End Time: {endTime}
          </p>
          <button className="assign-button" onClick={handleShowModal}>
            Assign Trainees
          </button>
        </div>
      </div>
        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>Assign Trainees - Batch ID: {batch.id}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form>
              <Form.Group>
                <Form.Label>Batch name</Form.Label>
                <Form.Control type="text" value={batch.batchName} readOnly />
                <Form.Label>Trainees</Form.Label>
                <Select
                  options={traineeOptions} // Provide options for trainees
                  isMulti
                  value={selectedValues}
                  onChange={handleSelectionChange}
                />
              </Form.Group>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Close
            </Button>
            <Button variant="primary" onClick={handleSubmit}>
              Save
            </Button>
            {successAssignMessage && <p className="error-message" style={{color:'green',fontSize:'14px','marginTop':'8px'}}>{successAssignMessage}</p>}
          </Modal.Footer>
        </Modal>
        
      
    </>
  );
};

export default BatchCard;

{/* <div style={{ marginTop: '20px', padding: '10px', border: '1px solid #ccc', borderRadius: '4px' }}>
        <h3>List of Items:</h3>
        <p>Batch ID: {batch.id}</p>
        {items.map((item, index) => (
          <div key={index}>
            
            <p>Trainee Email: {item.traineeEmail}</p>
            <hr />
          </div>
        ))}
      </div> */}