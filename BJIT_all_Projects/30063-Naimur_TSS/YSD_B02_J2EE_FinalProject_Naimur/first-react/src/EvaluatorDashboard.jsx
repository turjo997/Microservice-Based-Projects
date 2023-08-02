import React from 'react'
import { Button, Col, Container, Row } from 'reactstrap';
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Home from './components/Home'
import Header from './components/Header';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ApplicantAllCircular from './components/ApplicantAllCircular';
import ApplicantMenus from './components/ApplicantMenus';
import MyApplications from './components/MyApplications';
import UploadWrittenMarks from './components/UploadWritenMarks';
import EvaluatorMenus from './components/EvaluatorMenus';


function EvaluatorDashboard() {
  const btnHandle=()=>{
    toast("this is my first message");
  }
 return(<div >
  <Router>
  <ToastContainer/>
  <Container>
    <Header/>
    <Row>
      <Col md={4}>
        <EvaluatorMenus/>
      </Col>
      <Col md={8}>
        <Routes>
        <Route path="/" element={<Home/>} exact/>
      <Route path="/upload-marks" element={<UploadWrittenMarks/>} exact/>
      
      
        </Routes>
      </Col>
    </Row>
  </Container>

  </Router>
 
  
  </div>);
}
export default EvaluatorDashboard;