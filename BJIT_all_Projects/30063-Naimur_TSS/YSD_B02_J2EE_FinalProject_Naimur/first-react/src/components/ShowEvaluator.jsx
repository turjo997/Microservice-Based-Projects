import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import { toast } from 'react-toastify';

Modal.setAppElement('#root');

const getToken = () => {
  const token = localStorage.getItem('token');
  return token;
};

const ShowEvaluator = () => {
  const [evaluators, setEvaluators] = useState([]);
  const [selectedEvaluator, setSelectedEvaluator] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [detailedInfo, setDetailedInfo] = useState(null);
  const [newEvaluator, setNewEvaluator] = useState({
    firstName: '',
    lastName: '',
    gender: '',
    dob: '',
    email: '',
    contactNumber: '',
    specialization: '',
    password: ''
  });

  const [allCircularData, setAllCircularData] = useState([]);
  const [selectedCircular, setSelectedCircular] = useState(null);

  const [selectedEvaluatorForModal, setSelectedEvaluatorForModal] = useState(null);
  const [circularDetailsModalOpen, setCircularDetailsModalOpen] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    // Fetch data using Axios
    axios.get('http://localhost:8084/admin/showAllEvaluator', {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    })
      .then(response => {
        setEvaluators(response.data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });

    axios.get('http://localhost:8084/admin/circular/all', {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    })
      .then(response => {
        setAllCircularData(response.data);
      })
      .catch(error => {
        console.error('Error fetching all circular data:', error);
      });
  }, []);

  const handleViewDetail = (evaluator) => {
    setSelectedEvaluator(evaluator);

    axios.get(`http://localhost:8084/admin/showEvaluatorById/${evaluator.evaluatorId}`, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    })
      .then(response => {
        setDetailedInfo(response.data);
        setIsModalOpen(true); 
      })
      .catch(error => {
        console.error('Error fetching detailed information:', error);
      });
  };

  const handleAssignEvaluator = (evaluator) => {
    setSelectedEvaluatorForModal(evaluator); // 
    setCircularDetailsModalOpen(true);
  };

  const handleAssignCircular = (circular) => {
    if (selectedEvaluatorForModal && circular) {
      const data = {
        evaluatorId: selectedEvaluatorForModal.evaluatorId,
        circularId: circular.circularId,
      };

      axios.post('http://localhost:8084/admin/assign/evaluator', data, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          console.log('Evaluator assigned successfully:', response.data);

          toast.success("Evaluator assigned successfully");

          handleCloseCircularDetailsModal();
        })
        .catch(error => {
          console.error('Error assigning evaluator:', error);
        });
    }
  };

  const handleCloseCircularDetailsModal = () => {
    setCircularDetailsModalOpen(false);
    setSelectedCircular(null);
  };

  const handleOpenAddModal = () => {
    setIsModalOpen(true); 
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedEvaluator(null);
    setDetailedInfo(null);
    setNewEvaluator({
      firstName: '',
      lastName: '',
      gender: '',
      dob: '',
      email: '',
      contactNumber: '',
      specialization: '',
      password: ''
    });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setNewEvaluator({ ...newEvaluator, [name]: value });
  };

  const handleSaveEvaluator = () => {

    axios.post('http://localhost:8084/admin/evaluator/register', newEvaluator, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        setIsModalOpen(false);
        setNewEvaluator({
          firstName: '',
          lastName: '',
          gender: '',
          dob: '',
          email: '',
          contactNumber: '',
          specialization: '',
          password: ''
        });

        setEvaluators(prevEvaluators => [...prevEvaluators, response.data]);
      })
      .catch(error => {
        console.error('Error saving evaluator:', error);
      });
  };

  const boxStyle = {
    width: '200px',
    height: '200px',
    margin: '10px',
    padding: '10px',
    border: '1px solid #ccc',
    borderRadius: '5px',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  };

  return (
    <div>
      <h1>Show Evaluator</h1>
      {/* Add the "Add Evaluator" button */}
      <button onClick={handleOpenAddModal}>Add Evaluator</button>

      {/* Display the fetched data */}
      <div style={{ display: 'flex', flexWrap: 'wrap' }}>
        {evaluators.map(evaluator => (
          <div key={evaluator.evaluatorId} style={boxStyle}>
            <p>Id: {evaluator.evaluatorId}</p>
            <h6>{evaluator.firstName} {evaluator.lastName}</h6>
            <p>Specialization: {evaluator.specialization}</p>
            {/* Add the "View Detail" button */}
            <button onClick={() => handleViewDetail(evaluator)}>View Detail</button>
            {/* Add the "Assign" button */}
            <button onClick={() => handleAssignEvaluator(evaluator)}>Assign</button>
          </div>
        ))}
      </div>

      {/* Render the modal */}
      <Modal
        isOpen={isModalOpen}
        onRequestClose={handleCloseModal}
        contentLabel={selectedEvaluator ? "Selected Evaluator Details" : "Add Evaluator"}
      >
        {selectedEvaluator ? (
          
          <div>
            <h2>Selected Evaluator Details</h2>
            <p>Id: {selectedEvaluator.evaluatorId}</p>
            <p>Name: {selectedEvaluator.firstName} {selectedEvaluator.lastName}</p>
            <p>Gender: {detailedInfo ? detailedInfo.gender : ''}</p>
            <p>Date of Birth: {detailedInfo ? detailedInfo.dob : ''}</p>
            <p>Email: {detailedInfo ? detailedInfo.email : ''}</p>
            <p>Contact Number: {detailedInfo ? detailedInfo.contactNumber : ''}</p>
            <p>Specialization: {selectedEvaluator.specialization}</p>
            <button onClick={handleCloseModal}>Close</button>
          </div>
        ) : (
        
          <div>
            <h2>Add Evaluator</h2>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
              <label htmlFor="firstName">First Name:</label>
              <input type="text" name="firstName" value={newEvaluator.firstName} onChange={handleInputChange} placeholder="First Name" />

              <label htmlFor="lastName">Last Name:</label>
              <input type="text" name="lastName" value={newEvaluator.lastName} onChange={handleInputChange} placeholder="Last Name" />

              <label htmlFor="gender">Gender:</label>
              <input type="text" name="gender" value={newEvaluator.gender} onChange={handleInputChange} placeholder="Gender" />

              <label htmlFor="dob">Date of Birth:</label>
              <input type="text" name="dob" value={newEvaluator.dob} onChange={handleInputChange} placeholder="Date of Birth" />

              <label htmlFor="email">Email:</label>
              <input type="text" name="email" value={newEvaluator.email} onChange={handleInputChange} placeholder="Email" />

              <label htmlFor="contactNumber">Contact Number:</label>
              <input type="text" name="contactNumber" value={newEvaluator.contactNumber} onChange={handleInputChange} placeholder="Contact Number" />

              <label htmlFor="specialization">Specialization:</label>
              <input type="text" name="specialization" value={newEvaluator.specialization} onChange={handleInputChange} placeholder="Specialization" />

              <label htmlFor="password">Password:</label>
              <input type="password" name="password" value={newEvaluator.password} onChange={handleInputChange} placeholder="Password" />

              <button onClick={handleSaveEvaluator}>Save</button>
              <button onClick={handleCloseModal}>Close</button>
            </div>
          </div>
        )}
      </Modal>

      {/* Render the modal for circular details */}
      <Modal
        isOpen={circularDetailsModalOpen}
        onRequestClose={handleCloseCircularDetailsModal}
        contentLabel="Circular Details"
      >
        {selectedEvaluatorForModal && (
          <div>
            <h2>All Circular Data</h2>
            {allCircularData.map(circular => (
              <div key={circular.circularId}>
                <p>Circular ID: {circular.circularId}</p>
                <p>Title: {circular.circularName}</p>
                <p>Description: {circular.detail}</p>
                {/* Update the "OK" button onClick event to pass the circular */}
                <button onClick={() => handleAssignCircular(circular)}>OK</button>
              </div>
            ))}
            {/* The "Close" button should now call handleCloseCircularDetailsModal */}
            <button onClick={handleCloseCircularDetailsModal}>Close</button>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default ShowEvaluator;
