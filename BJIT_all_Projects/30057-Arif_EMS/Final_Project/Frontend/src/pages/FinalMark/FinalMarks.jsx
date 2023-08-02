import React, { useState } from 'react'
import { Form, Button, Alert } from 'react-bootstrap'

const FinalMarks = () => {
  const [dailyTask, setDailyTask] = useState('')
  const [miniProject, setMiniProject] = useState('')
  const [midTerm, setMidTerm] = useState('')
  const [finalProject, setFinalProject] = useState('')
  const [managerEvaluation, setManagerEvaluation] = useState('')
  const [hrEvaluation, setHrEvaluation] = useState('')

  const handleFormSubmit = (event) => {
    event.preventDefault()
    const total =
      dailyTask +
      miniProject +
      midTerm +
      finalProject +
      managerEvaluation +
      hrEvaluation

    if (total === 100) {
      // Here, you can add code to handle form submission and send data to the backend
      // For simplicity, we'll just log the form data for now.
      console.log({
        dailyTask,
        miniProject,
        midTerm,
        finalProject,
        managerEvaluation,
        hrEvaluation,
      })
    } else {
      alert('Total should be 100')
    }
  }

  const total =
    dailyTask +
    miniProject +
    midTerm +
    finalProject +
    managerEvaluation +
    hrEvaluation

  return (
    <Form onSubmit={handleFormSubmit}>
      <Form.Group controlId='dailyTask'>
        <Form.Label>Daily Task</Form.Label>
        <Form.Control
          type='number'
          step='0.01'
          placeholder='Enter daily task value'
          value={dailyTask}
          onChange={(e) => setDailyTask(parseFloat(e.target.value))}
        />
      </Form.Group>

      <Form.Group controlId='miniProject'>
        <Form.Label>Mini Project</Form.Label>
        <Form.Control
          type='number'
          step='0.01'
          placeholder='Enter mini project value'
          value={miniProject}
          onChange={(e) => setMiniProject(parseFloat(e.target.value))}
        />
      </Form.Group>

      <Form.Group controlId='midTerm'>
        <Form.Label>Midterm</Form.Label>
        <Form.Control
          type='number'
          step='0.01'
          placeholder='Enter midterm value'
          value={midTerm}
          onChange={(e) => setMidTerm(parseFloat(e.target.value))}
        />
      </Form.Group>

      <Form.Group controlId='finalProject'>
        <Form.Label>Final Project</Form.Label>
        <Form.Control
          type='number'
          step='0.01'
          placeholder='Enter final project value'
          value={finalProject}
          onChange={(e) => setFinalProject(parseFloat(e.target.value))}
        />
      </Form.Group>

      <Form.Group controlId='managerEvaluation'>
        <Form.Label>Manager Evaluation</Form.Label>
        <Form.Control
          type='number'
          step='0.01'
          placeholder='Enter manager evaluation value'
          value={managerEvaluation}
          onChange={(e) => setManagerEvaluation(parseFloat(e.target.value))}
        />
      </Form.Group>

      <Form.Group controlId='hrEvaluation'>
        <Form.Label>HR Evaluation</Form.Label>
        <Form.Control
          type='number'
          step='0.01'
          placeholder='Enter HR evaluation value'
          value={hrEvaluation}
          onChange={(e) => setHrEvaluation(parseFloat(e.target.value))}
        />
      </Form.Group>

      <div>
        <p>Total: {total}</p>
        {total === 100 ? (
          <Button variant='primary' type='submit'>
            Submit
          </Button>
        ) : (
          <Alert variant='danger'>Total should be 100</Alert>
        )}
      </div>
    </Form>
  )
}

export default FinalMarks
