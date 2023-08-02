import React,{useEffect, useState} from 'react'
import Sidebar from '../components/Sidebar';
// import { useDispatch, useSelector } from 'react-redux';
import ButtoNModalForm from '../components/courseComponents/ButtonModalForm';
import CourseCardList from '../components/courseComponents/CourseCardList';

export default function CoursePage() {
    const token = localStorage.getItem('token');
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [courses, setCourses] = useState([]);
    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };
    useEffect(() => {
        // Fetch courses from the Spring backend API endpoint
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
      }, []);
    
  return (
    <div>
        <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
        <div className= "container" style={{justifyContent:'center', marginTop:'70px', display:'flex'}}>
            <ButtoNModalForm />
        </div>
        <div style={{display:'flex', justifyContent:'center'}}><CourseCardList courses = {courses} /> </div>
    </div>
  )
}
