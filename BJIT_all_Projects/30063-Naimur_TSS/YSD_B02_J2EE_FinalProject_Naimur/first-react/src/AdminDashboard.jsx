import React from 'react'
import { Button, Col, Container, Row } from 'reactstrap';
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Home from './components/Home'
import Circular from './components/Circular';
import AllCirculars from './components/AllCirculars';
import AddCircular from './components/AddCircular';
import Header from './components/Header';
import Menus from './components/Menus';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import UpdateCircular from './components/UpdateCircular';
import ViewApplication from './components/VIewApplication';
import Evaluator from './components/Evaluator';
import SelectedCandidate from './components/SelectedCandidate';
import ShowEvaluator from './components/ShowEvaluator';
import FinalSelection from './components/FinalSelection';


function AdminDashboard() {
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
        <Menus/>
      </Col>
      <Col md={8}>
        <Routes>
        <Route path="/" element={<Home/>} exact/>
      <Route path="/add-circular" element={<AddCircular/>} exact/>
      <Route path="/view-circulars" element={<AllCirculars/>} exact/>
      <Route path="/update-circular/:circularId" element={<UpdateCircular/>} exact/>
      <Route path="/admin/application/:circularId" element={<ViewApplication />} exact />
      <Route path="/candidate" element={<SelectedCandidate />} exact />
      <Route path="/admin/showEvaluator" element={<ShowEvaluator/>} exact />
      <Route path="/admin/finalSelection" element={<FinalSelection/>} exact />
  

      
        </Routes>
      </Col>
    </Row>
  </Container>

  </Router>
 
  
  </div>);
}
export default AdminDashboard;