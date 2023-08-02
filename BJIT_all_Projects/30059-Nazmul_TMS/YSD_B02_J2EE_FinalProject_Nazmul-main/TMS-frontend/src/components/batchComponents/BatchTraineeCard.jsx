import React,{useEffect, useState} from 'react';
import { Card, Dropdown, Image } from 'react-bootstrap';
import './BatchTraineeCard.css';
import defaultBatchImage from './images/academy.png';
import { useDispatch, useSelector } from 'react-redux';
import { getBatchData } from '../../redux/batchRedux/batchActions';

const imageUrl = defaultBatchImage;

const BatchTraineeCard = () => {
    const dispatch = useDispatch();
    const batches = useSelector((state)=>state.batch.batches); 

  useEffect(()=>{
    dispatch(getBatchData());
  },[dispatch]);

  const [selectedBatch, setSelectedBatch] = useState('All Batches');
  const [trainees, setTrainees] = useState([]);

//   // Get unique batch names from trainees
//   const batchNames = ['All Batches', ...new Set(trainees.map((trainee) => trainee.batchName))];

  // Filter trainees based on selected batch
//   const filteredTrainees = selectedBatch === 'All Batches'
//     ? trainees
//     : trainees.filter((trainee) => trainee.batchName === selectedBatch);

  const handleBatchSelection = (batchName) =>{
    setSelectedBatch(batchName);
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8081/api/batch/trainees/${batchName}`,{
        method: 'GET',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            }
    })
    .then((response) => response.json())
    .then((data) => {
        console.log(data);
      // Set the trainee data in the state
    setTrainees(data); // Replace "data.trainees" with the actual response structure from your backend
    })
    .catch((error) => {
      // Handle the error
      console.error(error);
    });
  }
  return (
    <div>
        <h1 style={{marginTop:'50px', display:'flex',justifyContent:'center'}}>Trainees</h1>
      
        <div className="batch-dropdown">
        <Dropdown>
          <Dropdown.Toggle variant="secondary">
            {selectedBatch}
          </Dropdown.Toggle>
          <Dropdown.Menu>
            {batches.map((batch, index) => (
              <Dropdown.Item
                key={batch.batchId}
                active={selectedBatch === batch.batchName}
                onClick={() => handleBatchSelection(batch.batchName)}
              >
                {batch.batchName}
              </Dropdown.Item>
            ))}
          </Dropdown.Menu>
        </Dropdown>
      </div>
      <div className="card-container">
        {trainees.map((trainee, index) => (
          <Card key={index} className="trainee-card">
            <Card.Img variant="top" src= {`http://localhost:8081/api/user/images/${trainee.profilePicture}`} />
            <Card.Body>
              <Card.Title>{trainee.traineeEmail}</Card.Title>
              <Card.Text>Phone: {trainee.phoneNo}</Card.Text>
              <Card.Text>Batch: {trainee.batchName}</Card.Text>
            </Card.Body>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default BatchTraineeCard;
