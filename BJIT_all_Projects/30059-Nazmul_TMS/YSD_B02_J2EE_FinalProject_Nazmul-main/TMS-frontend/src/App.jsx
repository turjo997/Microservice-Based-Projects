// import './App.css'
// import { Routes, Route } from 'react-router-dom'
// import LoginPage from './pages/LoginPage'
// import Dashboard from './pages/Dashboard';
// import Userpage from './pages/Userpage';
// import Batchpage from './pages/Batchpage';
// import BatchTrainees from './pages/BatchTrainees';
// import CoursePage from './pages/CoursePage';
// import SchedulePage from './pages/SchedulePage';
// import Assignment from './pages/Assignment';
// import Classroom from './pages/Classroom';
// import ClassroomPage from './pages/ClassroomPage';

// function App() {
//   const token = localStorage.getItem('token');
  
//   return (
//     <>
//     {/* <ClassroomPage /> */}
    
//     {/* <DashboardHome/> */}
//       <Routes>
//       <Route path ="/" element = {<LoginPage/>} />
//       {token ? <Route path="/dashboard" element ={<Dashboard/>}></Route>:<Route path='/'/>}
      
//       {token ? <Route path="/users" element ={<Userpage/>}></Route>:<Route path='/'/>}
//       {token ? <Route path="/batch" element ={<Batchpage />}></Route>:<Route path='/'/>}
//       {token ? <Route path ="/batch/trainees" element = {<BatchTrainees/>}></Route>:<Route path='/'/>}
//       {token ? <Route path ="/course" element = {<CoursePage />}></Route>:<Route path='/'/>}
//       {token ? <Route path = "/schedules" element={<SchedulePage />}></Route>:<Route path = '/'/>}
//       {token ? <Route path = "/assignments" element={<Assignment />}></Route>:<Route path = '/'/>}
//       {token ? <Route path = "/classrooms" element = {<Classroom />}></Route>:<Route path = '/'/>}
//       {token ? <Route path="/classrooms/:classroomId" element={<ClassroomPage />}></Route>:<Route path = '/'/>}
//       </Routes>
      
//     </>
//   )
// }

// export default App
import React, { useEffect, useState } from 'react';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSpinner } from '@fortawesome/free-solid-svg-icons';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import Userpage from './pages/Userpage';
import Batchpage from './pages/Batchpage';
import BatchTrainees from './pages/BatchTrainees';
import CoursePage from './pages/CoursePage';
import SchedulePage from './pages/SchedulePage';
import Assignment from './pages/Assignment';
import Classroom from './pages/Classroom';
import ClassroomPage from './pages/ClassroomPage';
import ChatPage from './pages/ChatPage';

function App() {
  const token = localStorage.getItem('token');
  const [loading, setLoading] = useState(true);

  // Simulate loading by using setTimeout (replace with actual data fetching logic)
  useEffect(() => {
    setTimeout(() => {
      setLoading(false); // Set loading to false after data fetching (simulated here with setTimeout)
    }, 2000); // Simulated loading delay of 2 seconds
  }, []);

  return (
    <>
    {/* <ClassroomPage /> */}
    
    {/* <DashboardHome/> */}
      <Routes>
      <Route path ="/" element = {<LoginPage/>} />
      {token ? <Route path="/dashboard" element ={<Dashboard/>}></Route>:<Route path='/'/>}
      
      {token ? <Route path="/users" element ={<Userpage/>}></Route>:<Route path='/'/>}
      {token ? <Route path="/batch" element ={<Batchpage />}></Route>:<Route path='/'/>}
      {token ? <Route path ="/batch/trainees" element = {<BatchTrainees/>}></Route>:<Route path='/'/>}
      {token ? <Route path ="/course" element = {<CoursePage />}></Route>:<Route path='/'/>}
      {token ? <Route path = "/schedules" element={<SchedulePage />}></Route>:<Route path = '/'/>}
      {token ? <Route path = "/assignments" element={<Assignment />}></Route>:<Route path = '/'/>}
      {token ? <Route path = "/classrooms" element = {<Classroom />}></Route>:<Route path = '/'/>}
      {token ? <Route path="/classrooms/:classroomId" element={<ClassroomPage />}></Route>:<Route path = '/'/>}
      <Route path = "/chatPage" element = {<ChatPage />}/>
      </Routes>
      
    </>
  );
}

export default App;
