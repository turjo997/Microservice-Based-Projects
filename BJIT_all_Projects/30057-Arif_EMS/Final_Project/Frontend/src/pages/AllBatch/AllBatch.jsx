import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ListGroup, Button, Modal, Form, Alert } from 'react-bootstrap';

const AllBatch = ({ userId }) => {
  const userIdfinal = parseInt(userId);
  const [batches, setBatches] = useState([]);
  const [showTraineeModal, setShowTraineeModal] = useState(false);
  const [selectedBatchTrainees, setSelectedBatchTrainees] = useState([]);
  const [showCreateTaskModal, setShowCreateTaskModal] = useState(false);
  const [taskTitle, setTaskTitle] = useState('');
  const [taskType, setTaskType] = useState('Daily Task');
  const [taskDate, setTaskDate] = useState('');
  const [question, setTaskFile] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);

  useEffect(() => {
    // Fetch batch data from the API
    axios.get(`http://localhost:8080/view/all/batches/${userIdfinal}`)
      .then((response) => {
        setBatches(response.data);
      })
      .catch((error) => {
        console.error('Error fetching batches:', error);
      });
  }, []);

  // Function to convert timestamp to local date and time
  const convertToLocalTime = (timestamp) => {
    const dateObj = new Date(timestamp);
    return dateObj.toLocaleString();
  }

  // Function to handle the "View Trainees" button click
  const handleViewTrainees = (trainees) => {
    setSelectedBatchTrainees(trainees);
    setShowTraineeModal(true);
  };

  // Function to handle the "Create Task" button click
  const handleCreateTask = () => {
    setShowCreateTaskModal(true);
  };

  // Function to handle the file input change
  const handleFileChange = (e) => {
    setTaskFile(e.target.files[0]);
  };

  // Function to handle the task form submission
  const handleSubmitTask = (e) => {
    e.preventDefault();
  
    // Create a new FormData object
    const formData = new FormData();
  
    // Append the task data to the FormData object
    const datePart = taskDate.split('T')[0];
    const taskDateObj = new Date(datePart); // Convert date string to Date object
    formData.append('taskTitle', taskTitle);
    formData.append('taskType', taskType);
    formData.append('taskDate', taskDateObj); // Append the task date as Date object
    formData.append('question', question); // Append the task file
  
    // Get the batchId from the first selected batch trainee
    const batchId = selectedBatchTrainees[0]?.batchId;
  
    if (!batchId) {
      // Show an error message if no batch is selected
      setShowErrorMessage(true);
      return;
    }
  
    // Make the API call to create the task with multipart form data
    axios.post(`http://localhost:8080/task/create/${batchId}/${userIdfinal}`, formData)
      .then((response) => {
        // Show success message and close the modal
        setShowSuccessMessage(true);
        setShowCreateTaskModal(false);
        // Clear the form inputs
        setTaskTitle('');
        setTaskType('Daily Task');
        setTaskDate('');
        setTaskFile(null); // Clear the task file state
        // Hide the success message after 3 seconds
        setTimeout(() => setShowSuccessMessage(false), 3000);
      })
      .catch((error) => {
        console.error('Error creating task:', error);
        // Show error message
        setShowErrorMessage(true);
      });
  };
  return (
    <div>
      <h1>Your Batch</h1>
      <ListGroup>
        {batches.map((batch) => (
          <ListGroup.Item key={batch.batchId}>
            <h5>{batch.batchName}</h5>
            <p>Start Date: {convertToLocalTime(batch.startDate)}</p>
            <Button variant="primary" onClick={() => handleViewTrainees(batch.trainees)}>
              View Trainees
            </Button>
            <Button variant="success" onClick={handleCreateTask}>
              Create Task
            </Button>
          </ListGroup.Item>
        ))}
      </ListGroup>

      {/* Modal to display the trainees */}
      <Modal show={showTraineeModal} onHide={() => setShowTraineeModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Trainees List</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ListGroup>
            {selectedBatchTrainees.map((trainee) => (
              <ListGroup.Item key={trainee.traineeId}>
                <h5>{trainee.user.fullName}</h5>
                <p>Institute: {trainee.institute}</p>
                <p>CGPA: {trainee.cgpa}</p>
              </ListGroup.Item>
            ))}
          </ListGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowTraineeModal(false)}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={showCreateTaskModal} onHide={() => setShowCreateTaskModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Create Task</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmitTask}>
            <Form.Group controlId="taskTitle">
              <Form.Label>Task Title</Form.Label>
              <Form.Control
                type="text"
                value={taskTitle}
                onChange={(e) => setTaskTitle(e.target.value)}
                required
              />
            </Form.Group>
            <Form.Group controlId="taskType">
              <Form.Label>Task Type</Form.Label>
              <Form.Control
                as="select"
                value={taskType}
                onChange={(e) => setTaskType(e.target.value)}
              >
                <option value="Daily Task">Daily Task</option>
                <option value="Mid Term">Mid Term</option>
                <option value="Mini Project">Mini Project</option>
                <option value="Final Project">Final Project</option>
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="taskDate">
              <Form.Label>Task Date</Form.Label>
              <Form.Control
                type="datetime-local"
                value={taskDate}
                onChange={(e) => setTaskDate(e.target.value)}
                required
              />
            </Form.Group>
            <Form.Group controlId="question">
              <Form.Label>Task File</Form.Label>
              <Form.Control type="file" onChange={handleFileChange} />
            </Form.Group>
            <Button variant="primary" type="submit">
              Create Task
            </Button>
          </Form>
        </Modal.Body>
        {/* ... (previous code) */}
      </Modal>

      {/* Success message */}
      {showSuccessMessage && (
        <Alert variant="success" className="mt-3">
          Task created successfully!
        </Alert>
      )}

      {/* Error message */}
      {showErrorMessage && (
        <Alert variant="danger" className="mt-3">
          Error creating task. Please try again.
        </Alert>
      )}
    </div>
  );
};

export default AllBatch;
