import { format } from 'date-fns';
import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import ReactDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { useDispatch, useSelector } from 'react-redux';
import { createBatch } from '../../redux/batchRedux/batchActions';


const ButtoNModalForm = () => {
  const [showModal, setShowModal] = useState(false);
  const [batchName, setBatchName] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [isBatchNameValid, setIsBatchNameValid] = useState(true);
    const dispatch = useDispatch();
    const error = useSelector((state)=>state.batch.error);
    const successMessage = useSelector((state)=>state.batch.successMessage);
  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === 'batchName') {
      setBatchName(value);
      setIsBatchNameValid(true);
    } else if (name === 'startTime') {
      setStartTime(value);
    } else if (name === 'endTime') {
      setEndTime(value);
    }
  };
  const handleStartTimeChange = (date) => {
    setStartTime(date);
  };

  const handleEndTimeChange = (date) => {
    setEndTime(date);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const batchData = {
      batchName,
      startTime,
      endTime,
    };
    console.log(startTime);
    const batchNameRegex = /^YSD-[a-zA-Z]+-B\d{2}$/;
    const isValid = batchNameRegex.test(batchName.toUpperCase());
    
    if(isValid){
        console.log("Batch name is in correct format");
        dispatch(createBatch(batchData));
    }
    else{
        const errorData = "Wrong format";
        dispatch({ type: "CREATE_BATCH_ERROR", payload: errorData });
    }
  }
  return (
    <>
      <button style={{ backgroundColor: 'green' }} onClick={handleShowModal}>
        Add Batch
      </button>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Add Batch</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="batchName">
              <Form.Label>Batch Name</Form.Label>
              <Form.Control
                type="text"
                name="batchName"
                value={batchName}
                onChange={handleInputChange}
                required
              />
              {!isBatchNameValid && 
              <Form.Control.Feedback type="invalid">
              Please enter a valid batch name in the format "YSD-subject-B01".
            </Form.Control.Feedback>
            }
              <Form.Text muted>
                Format: ysd-subject-b(1-9) (e.g., ysd-english-b01)
              </Form.Text>
            </Form.Group>
            <Form.Group controlId="startTime">
              <Form.Label>Start Time</Form.Label>
              <ReactDatePicker
                selected={startTime}
                onChange={handleStartTimeChange}
                // showTimeSelect
                // timeFormat="HH:mm"
                // timeIntervals={15}
                dateFormat="MMMM d, yyyy"
                name="startTime"
                className="form-control"
                required
              />
            </Form.Group>
            <Form.Group controlId="endTime">
              <Form.Label>End Time</Form.Label>
              <ReactDatePicker
                selected={endTime}
                onChange={handleEndTimeChange}
                // showTimeSelect
                // timeFormat="HH:mm"
                // timeIntervals={15}
                dateFormat="MMMM d, yyyy"
                name="endTime"
                className="form-control"
                required
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Add
            </Button>
            {successMessage && <p className="error-message" style={{color:'green',fontSize:'14px','marginTop':'8px'}}>{successMessage}</p>}
            {error && <p className="error-message" style={{color:'red',fontSize:'14px','marginTop':'8px'}}>{error}</p>}
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default ButtoNModalForm;
// const sanitizedValue = value.replace(/[^a-zA-Z0-9-]/g, '').toUpperCase();
//         console.log(sanitizedValue);
//   // Apply the format "YSD-subject-B01"
//   const formattedValue = /^YSD-[a-zA-Z]+-B\d{2}$/.test(sanitizedValue)
//     ? sanitizedValue
//     : `YSD-${sanitizedValue}-B01`;
// console.log(formattedValue);
//   setBatchName(formattedValue)
//