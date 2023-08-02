import React, { useEffect, useState } from "react";
import ReactModal from "react-modal";
import Select from "react-select";
import { Form, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import './ScheduleCreateModal.css';
import { useDispatch, useSelector } from "react-redux";
import { createSchedule } from "../../redux/scheduleRedux/scheduleActions";

function ScheduleCreateModal({ isModalOpen, closeModal, courses }) {
  const [formValues, setFormValues] = useState({});
  const [selectedValues, setSelectedValues] = useState([]);
  
  const dispatch = useDispatch();
  const trainers = useSelector((state)=>state.schedule.trainers);
  const batches = useSelector((state)=>state.batch.batches);
  const error = useSelector((state)=>state.schedule.error);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("clicked");
    // Perform form submission logic
    const newEvent = {
      scheduleName:formValues.scheduleName,
      startTime:formValues.startTime,
      endTime:formValues.endTime,
      courseTitle:formValues.courseTitle,
      batchName:formValues.batchName,
      trainerEmail:formValues.trainerEmail,
    }
    console.log("new event: ",newEvent)
    // console.log(startTime, endTime, course, batchNames, trainerEmail);
    dispatch(createSchedule(newEvent));
    closeModal();
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues((prevFormValues) => ({
      ...prevFormValues,
      [name]: value,
    }));
  };

  const handleSelectionChange = (selectedOptions) => {
    const isArray = Array.isArray(selectedOptions);

  // If isMulti is true, map the selectedOptions to an array of batch names
  const batchName = isArray
    ? selectedOptions.map((option) => option.value)
    : selectedOptions.value;

  // Update formValues with the selected batchNames
  setFormValues({ ...formValues, batchName });
    setSelectedValues(selectedOptions);
  };
  const batchOptions = batches.map((batch) => ({
    value: batch.batchName,
    label: batch.batchName,
  }));
  return (
    <div>
    <ReactModal
      isOpen={isModalOpen}
      onRequestClose={closeModal}
      className="modal-container"
      overlayClassName="modal-overlay"
    >
        <button className="close-button" onClick={closeModal}>
        <FontAwesomeIcon icon={faTimes} />
      </button>
      <h2>Create Schedule</h2>
      <Form onSubmit={handleSubmit} className="form-container">
        <Form.Group controlId="scheduleName">
          <Form.Label>Schedule Name:</Form.Label>
          <Form.Control
            type="text"
            name="scheduleName"
            value={formValues.scheduleName || ""}
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Group controlId="startTime">
          <Form.Label>Start Time:</Form.Label>
          <Form.Control
            type="datetime-local"
            name="startTime"
            value={formValues.startTime || ""}
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Group controlId="endTime">
          <Form.Label>End Time:</Form.Label>
          <Form.Control
            type="datetime-local"
            name="endTime"
            value={formValues.endTime || ""}
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Group controlId="courseTitle">
          <Form.Label>Course:</Form.Label>
          <Form.Control
            as="select"
            name="courseTitle"
            value={formValues.courseTitle || ""}
            onChange={handleChange}
          >
            <option value="">Select a course</option>
            {courses.map((course) => (
            <option key={course.id} value={course.courseTitle}>
              {course.courseTitle}
            </option>
          ))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="batchName">
          <Form.Label>Batch Name:</Form.Label>
          <Select
                options={batchOptions} // Provide options for trainees
                isMulti
                value={selectedValues}
                onChange={handleSelectionChange}
        />
        </Form.Group>
        <Form.Group controlId="trainerEmail">
          <Form.Label>Trainer Email:</Form.Label>
          <Form.Control
            as="select"
            name = "trainerEmail"
            value={formValues.trainerEmail || ""}
            onChange={handleChange}
          >
            <option value="">Select a trainer</option>
            {trainers.map((trainer) => (
            <option key={trainer.id} value={trainer.email}>
              {trainer.email}
            </option>
          ))}
            {/* Add options for trainer emails */}
          </Form.Control>
        </Form.Group>
        <Button type="submit" variant="primary" className="submit-button">
          Submit
        </Button>
      
      </Form>
      {error && <p style={{color:'red'}}>{error}</p>}

      {/* <Button onClick={closeModal} variant="secondary" style={{position: 'absolute', top: '10px', right: '10px', cursor: 'pointer'}}>
        Close
      </Button> */}
    </ReactModal>
    </div>
  );
}

export default ScheduleCreateModal;
