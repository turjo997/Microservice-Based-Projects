import React, { useState, useEffect } from 'react'
import Modal from "react-modal";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { Form, Button } from 'react-bootstrap';
import Select from "react-select";
import { useDispatch, useSelector } from 'react-redux';
import { getAllTrainers } from '../../redux/scheduleRedux/scheduleActions';
import { getBatchData } from '../../redux/batchRedux/batchActions';

export default function ScheduleInformationModal({ clickedEvent, isModalOpen, closeModal, role }) {
  const customModalStyles = {
    content: {
      backgroundColor: 'white',
      width: '400px',
      height: '500px',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      zIndex: '1',
      boxShadow: '0px 4px 15px rgba(0, 0, 0, 0.2)', // Add a shadow to the modal
      borderRadius: '8px', // Add border radius for rounded corners

    },
    overlay: {
      backgroundColor: 'rgba(0, 0, 0, 0.5)', // Add an overlay to darken the background
      zIndex: '2'
    },
  };
  const token = localStorage.getItem('token');
  const [isUpdateModalOpen, setUpdateModalOpen] = useState(false);
  const [updatedEvent, setUpdatedEvent] = useState({});
  const [courses, setCourses] = useState([]);
  const[successMessage, setSuccessMessage] = useState('');
  const openUpdateModal = () => {
    setUpdateModalOpen(true);
  };
  const [selectedBatch, setSelectedBatch] = useState([]);
  const dispatch = useDispatch();
  const trainers = useSelector((state)=>state.schedule.trainers)
  const batches = useSelector((state)=>state.batch.batches);

  
  
  useEffect(()=>{
    dispatch(getAllTrainers("TRAINER"));
    dispatch(getBatchData());
    fetch('http://localhost:8081/api/course/get-courses',{
            method: 'GET',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            }
    
        })
          .then((response) => response.json())
          .then((data) => setCourses(data))
          .catch((error) => console.error('Error fetching courses:', error));
  },[]);
  
  const batchOptions = batches.map((batch) => ({
    value: batch.batchName,
    label: batch.batchName,
  }));
  // Function to handle batch selection
  
  const handleSelectionChange = (selectedOptions) => {
    const isArray = Array.isArray(selectedOptions);

  // If isMulti is true, map the selectedOptions to an array of batch names
  const batchName = isArray
      ? selectedOptions.map((option) => option.value)
      : selectedOptions.value;

    // Update selectedBatch state with the selected batch names
    setSelectedBatch(selectedOptions);

    // Update updatedEvent state with the selected batch names
    setUpdatedEvent((prevEvent) => ({
      ...prevEvent,
      batchName,
    }));
  };
  const closeUpdateModal = () => {
    setUpdateModalOpen(false);
  };
  const handleUpdate = () => {
    const updatedEventData = {
      title: updatedEvent.title,
      trainerEmail: updatedEvent.trainerEmail,
      courseTitle: updatedEvent.courseTitle,
      batchName: updatedEvent.batchName,
    };
    // console.log(updatedEventData);
    fetch(`http://localhost:8081/api/schedules/update-schedule/${updatedEvent.scheduleId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `${token}`,
    },
    body: JSON.stringify(updatedEventData),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to update the event.');
      }
      return response.text();
      // If the update is successful, close the update modal and refresh the calendar if needed
      
    }).then((data)=>{
        console.log("response:", data);
        setSuccessMessage(data);
        closeUpdateModal();
    })
    .catch((error) => {
      console.error('Error updating the event:', error);
      // Handle the error or display an error message to the user
    });


    
    closeUpdateModal();
  };
  useEffect(() => {
    // Update the 'updatedEvent' state whenever the 'clickedEvent' prop changes
    setUpdatedEvent({
      title: clickedEvent.title,
      start: clickedEvent.start,
      end: clickedEvent.end,
      trainerEmail: clickedEvent.extendedProps.trainerEmail,
      batchName: clickedEvent.extendedProps.batchName || [], // Ensure that batchName is an array
      scheduleId: clickedEvent.extendedProps.scheduleId,
      courseTitle: clickedEvent.extendedProps.courseTitle,
    });

    // Preselect batch options
    setSelectedBatch(clickedEvent.extendedProps.batchName || []);
    setUpdatedEvent({ ...clickedEvent.extendedProps });
  }, [clickedEvent]);


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedEvent((prevEvent) => ({
      ...prevEvent,
      [name]: value,
    }));
  };

  const handleDelete = (scheduleId) => {
    // Make an API call to the backend to delete the schedule with the given scheduleId
    fetch(`http://localhost:8081/api/schedules/delete-schedule/${scheduleId}`, {
      method: 'DELETE',
      headers: {
        Authorization: `${token}`,
      },
    })
      .then((response) => {
        console.log(response.text());
        if (!response.ok) {
          throw new Error('Failed to delete the schedule.');
        }
        // If the delete is successful, close the update modal and refresh the calendar if needed
        closeUpdateModal();
      })
      .catch((error) => {
        console.error('Error deleting the schedule:', error);
      });
  };
  
  return (
    <>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        style={customModalStyles}
      >
        {clickedEvent && (
          <div>
            <h2>{clickedEvent.title}</h2>
            <p>
              <strong>Start:</strong> {clickedEvent.start && clickedEvent.start.toLocaleString()}
            </p>
            <p>
              <strong>End:</strong> {clickedEvent.end && clickedEvent.end.toLocaleString()}
            </p>
            <p>
              <strong>Trainer Email:</strong> {clickedEvent.extendedProps.trainerEmail}
            </p>
            <p>
              <strong>Batch Name:</strong> {clickedEvent.extendedProps.batchName}
            </p>
            <p>
              <strong>Schedule ID:</strong> {clickedEvent.extendedProps.scheduleId}
            </p>
            <p>
              <strong>Course Title:</strong> {clickedEvent.extendedProps.courseTitle}
            </p>
            <FontAwesomeIcon
              icon={faTimes}
              style={{
                position: 'absolute',
                top: '10px',
                right: '10px',
                fontSize: '20px',
                cursor: 'pointer',
              }}
              onClick={closeModal} // Close the modal on click
            />
            {role==="ADMIN" && (<button
              style={{
                backgroundColor: 'blue',
                color: 'white',
                padding: '10px 20px',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer',
                marginTop: '10px',
              }}
              onClick={openUpdateModal}
            >
              Update Event
            </button>)}
            {successMessage && <p style={{color:'green', marginLeft:'80px'}}>{successMessage}</p>}
            {role==="TRAINER" &&(<Button variant="secondary" onClick={closeModal} >Create assignment</Button>)}
          </div>
        )}
      </Modal>
      <Modal isOpen={isUpdateModalOpen} onRequestClose={closeModal} style={customModalStyles} >
        {clickedEvent && (
          <Form onSubmit={handleUpdate}>
            <h2>{clickedEvent.title}</h2>
            <Form.Group controlId="title">
          <Form.Label>Schedule Name:</Form.Label>
          <Form.Control
            type="text"
            name="title"
            value={updatedEvent.title || ""}
            onChange={handleInputChange}
          />
        </Form.Group>
            <Form.Group controlId="trainerEmail">
              <Form.Label>Trainer email:</Form.Label>
              <Form.Control
                as="select"
                name="trainerEmail"
                value={updatedEvent.trainerEmail}
                onChange={handleInputChange}
              >
                <option value={clickedEvent.extendedProps.trainerEmail}>
                  {clickedEvent.extendedProps.trainerEmail}
                </option>
                {trainers.map((trainer) => (
            <option key={trainer.id} value={trainer.email}>
              {trainer.email}
            </option>
          ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="batchName">
              <Form.Label>Batch Name:</Form.Label>
              <Select  //...batchOptions
                options={[{ label: clickedEvent.extendedProps.batchName, value: clickedEvent.extendedProps.batchName }, ...batchOptions ]} // Provide options for batch names here
                isMulti
                value={selectedBatch} // Set the selected batch names here
                onChange={handleSelectionChange} // Handle selection changes if needed
              />
            </Form.Group>

            <Form.Group controlId="scheduleId">
              <Form.Label>Schedule ID:</Form.Label>
              <Form.Control
                type="text"
                name="scheduleId"
                value={updatedEvent.scheduleId}
                readOnly
              />
            </Form.Group>

            <Form.Group controlId="courseTitle">
              <Form.Label>Course Title:</Form.Label>
              <Form.Control
                as="select"
                name="courseTitle"
                value={updatedEvent.courseTitle}
                onChange={handleInputChange}
              >
<option value={clickedEvent.extendedProps.courseTitle}>
                  {clickedEvent.extendedProps.courseTitle}
                </option>
                {courses.map((course) => (
            <option key={course.id} value={course.courseTitle}>
              {course.courseTitle}
            </option>))}
              </Form.Control>
            </Form.Group>


            {/* Add other form controls for additional fields */}
            {/* ... */}

            {role==="ADMIN" && (<Button variant="primary" type="submit">
              Update
            </Button>)}
            {role==="ADMIN" && (<Button variant="danger" onClick={() => handleDelete(clickedEvent.extendedProps.scheduleId)}>
        Delete
      </Button>)}
      
      {role==="ADMIN" && (<Button variant="secondary" onClick={closeModal}>
              Cancel
            </Button>)}
          </Form>
        )}
      </Modal>

    </>
  )
}
