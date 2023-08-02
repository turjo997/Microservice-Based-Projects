import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';

const CreateBatch = () => {
    const [batchData, setBatchData] = useState({
        batchName: '',
        startDate: '',
        traineeIds: [],
        trainerIds: [],
      });
    
      const handleSubmit = async (event) => {
        event.preventDefault();
        try {
          // Make a POST request to the API endpoint
          const response = await fetch('http://localhost:8080/batch/create', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(batchData),
          });
          console.log(setBatchData);
    
          if (response.ok) {
            // Success, do something with the response if needed
            console.log('Batch created successfully!');
          } else {
            // Handle error responses here
            console.error('Error creating batch:', response.statusText);
          }
        } catch (error) {
          // Handle any network or other errors
          console.error('Error:', error);
        }
      };
    
      const handleInputChange = (event) => {
        const { name, value } = event.target;
        setBatchData({ ...batchData, [name]: value });
      };
    
      const handleTraineeSelectChange = (event) => {
        const selectedOptions = Array.from(event.target.selectedOptions, (option) => option.value);
        setBatchData({ ...batchData, traineeIds: selectedOptions });
      };
    
      const handleTrainerSelectChange = (event) => {
        const selectedOptions = Array.from(event.target.selectedOptions, (option) => option.value);
        setBatchData({ ...batchData, trainerIds: selectedOptions });
      };
    

  return (
    <div className="container mt-5">
      <h2>Create Batch</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="batchName">
          <Form.Label>Batch Name</Form.Label>
          <Form.Control
            type="text"
            name="batchName"
            value={batchData.batchName}
            onChange={handleInputChange}
            required
            placeholder="Enter Batch Name"
          />
        </Form.Group>
        <Form.Group controlId="startDate">
          <Form.Label>Start Date</Form.Label>
          <Form.Control
            type="date"
            name="startDate"
            value={batchData.startDate}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="traineeIds">
          <Form.Label>Trainee IDs</Form.Label>
          <Form.Control
            as="select"
            multiple
            name="traineeIds"
            value={batchData.traineeIds}
            onChange={handleTraineeSelectChange}
            required
          >
            <option value="1">Trainee 1</option>
            <option value="2">Trainee 2</option>
            <option value="3">Trainee 3</option>
            {/* Add more options here for trainees */}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="trainerIds">
          <Form.Label>Trainer IDs</Form.Label>
          <Form.Control
            as="select"
            multiple
            name="trainerIds"
            select
            value={batchData.trainerIds}
            onChange={handleTrainerSelectChange}
            required
          >
            <option value="101">Trainer 101</option>
            <option value="102">Trainer 102</option>
            <option value="103">Trainer 103</option>
            {/* Add more options here for trainers */}
          </Form.Control>
        </Form.Group>
        <Button variant="primary" type="submit">
          Create Batch
        </Button>
      </Form>
    </div>
  );
};

export default CreateBatch;
