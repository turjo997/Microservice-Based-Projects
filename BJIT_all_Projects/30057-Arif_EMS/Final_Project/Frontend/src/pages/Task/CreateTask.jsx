import React, { useState } from "react";
import { Form, Button } from "react-bootstrap";
import axios from "axios";

const CreateTask = ({role}) => {
  
  const [taskTitle, setTaskTitle] = useState("");
  const [taskType, setTaskType] = useState("");
  const [taskDate, setTaskDate] = useState("");

  const handleFormSubmit = (event) => {
    event.preventDefault();
    // Here, you can add code to handle form submission and send data to the backend
    // using axios or any other method.
    // For simplicity, we'll just log the form data for now.
    console.log({
      taskTitle,
      taskType,
      taskDate,
    });
  };

  return (
    <div className="container">
      <Form onSubmit={handleFormSubmit}>
        <Form.Group controlId="taskTitle">
          <Form.Label className="form-label">Task Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter task title"
            value={taskTitle}
            onChange={(e) => setTaskTitle(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="taskType">
          <Form.Label className="form-label">Task Type</Form.Label>
          <Form.Control
            as="select"
            value={taskType}
            onChange={(e) => setTaskType(e.target.value)}
          >
            <option value="">Select task type</option>
            <option value="Personal">Personal</option>
            <option value="Work">Work</option>
            <option value="Study">Study</option>
            <option value="Other">Other</option>
          </Form.Control>
        </Form.Group>

        <Form.Group controlId="taskDate">
          <Form.Label className="form-label">Task Date</Form.Label>
          <Form.Control
            type="date"
            placeholder="Select task date"
            value={taskDate}
            onChange={(e) => setTaskDate(e.target.value)}
          />
        </Form.Group>

        <Button variant="primary" type="submit" className="btn-submit">
          Submit
        </Button>
      </Form>
    </div>
  );
};

export default CreateTask;
