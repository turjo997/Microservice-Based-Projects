import { format } from 'date-fns';
import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import 'react-datepicker/dist/react-datepicker.css';

const ButtoNModalForm = () => {
    const [showModal, setShowModal] = useState(false);
    const [courseName, setCourseName] = useState('');
    const [courseDescription, setCourseDescription] = useState('');
    const [message, setMessage] = useState('');

    const token = localStorage.getItem('token');

    const handleShowModal = () => {
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        if (name === 'courseName') {
            setCourseName(value);
        } else if (name === 'courseDescription') {
            setCourseDescription(value);
        }
    };
    const handleSubmit = (e) => {
        e.preventDefault();
        const courseData = {courseName, courseDescription};
        fetch('http://localhost:8081/api/course/create-course',{
            method: 'POST',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(courseData),
    
        })
          .then((response) => response.json())
          .then((message) =>setMessage("Course Added successfully"))
          .catch((error) => console.error('Error fetching courses:', error));
    }
    return (
        <>
            <button style={{ backgroundColor: 'green' }} onClick={handleShowModal}>
                Add Course
            </button>

            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Course</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="courseName">
                            <Form.Label>Course Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="courseName"
                                value={courseName}
                                onChange={handleInputChange}
                                required
                            />
                            {/* {!isBatchNameValid && 
              <Form.Control.Feedback type="invalid">
              Please enter a valid batch name in the format "YSD-subject-B01".
            </Form.Control.Feedback>
            } */}
                            {/* <Form.Text muted>
                Format: ysd-subject-b(1-9) (e.g., ysd-english-b01)
              </Form.Text> */}
                        </Form.Group>
                        <Form.Group controlId="courseDescription">
                            <Form.Label>Course Description</Form.Label>
                            <Form.Control as="textarea" rows={3} name="courseDescription" value={courseDescription} onChange={handleInputChange} />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Add
                        </Button>
                        {message && <p className="error-message" style={{color:'green',fontSize:'14px','marginTop':'8px'}}>{message}</p>}
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