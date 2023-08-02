import { useEffect, useState } from "react";
import ScheduleCreateModal from "../components/scheduleComponents/ScheduleCreateModal";
import ScheduleInformationModal from "../components/scheduleComponents/ScheduleInformationModal";
import Sidebar from "../components/Sidebar";
import Calendar from "../components/scheduleComponents/Calendar";
import './SchedulePage.css';
import { useDispatch, useSelector } from "react-redux";
import { getAllSchedules, getAllTraineeSchedules, getAllTrainerSchedules, getAllTrainers } from "../redux/scheduleRedux/scheduleActions";
import { getBatchData } from "../redux/batchRedux/batchActions";
import { getUserRole } from "../redux/authRedux/authActions";

function SchedulePage() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [courses, setCourses] = useState([]);
  const token = localStorage.getItem('token');
  const [eventInfo, setEventInfo] = useState({
    clickedEvent: null,
    isModalOpen: false,
  });
  const dispatch = useDispatch();
  const role = useSelector((state)=>state.auth.role);
  console.log(role);
  useEffect(()=>{
  
    if(role==="ADMIN"){
      
    dispatch(getAllSchedules());
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
    } else if(role==="TRAINEE"){
      dispatch(getAllTraineeSchedules());

    }
    else if(role==="TRAINER"){
      dispatch(getAllTrainerSchedules());
    }
  },[]);
  const schedules = useSelector((state)=>state.schedule.schedules);
  
  const transformedEvents =  schedules?.map((schedule) => ({
    title: schedule?.scheduleName,
    start: schedule?.startTime,
    end: schedule?.endTime,
    scheduleId:schedule?.id,
    courseTitle:schedule?.courseTitle,
    batchName:schedule?.batchName,
    trainerEmail:schedule?.trainerEmail,
  }));

  console.log(schedules);
  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const handleEventClick = (info) => {
    setEventInfo({
      clickedEvent: info.event,
      isModalOpen: true,
    });
  };
  //style={{marginLeft:'70px',width:'700px',marginTop:'80px'}}
  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    // setClickedEvent(null);
    setIsModalOpen(false);
  };
  
  //     setEvents((prevEvents) => [...prevEvents, customEvent]);
  //   };

  
  return (
    <>
      <div className="app-container">
        <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
        <div
          className={`content-container ${isSidebarOpen ? "sidebar-open" : ""}`}
        >
          <h1>Schedule App</h1>
          <div className="calendar-container">
              <Calendar
                transformedEvents={transformedEvents}
                handleEventClick={handleEventClick}
                isSidebarOpen={isSidebarOpen}
              />
             
              
            </div>
          {role==="ADMIN" && (<button onClick={openModal}>Create Custom Event</button>)}
          {isModalOpen && (<ScheduleCreateModal isModalOpen={isModalOpen} closeModal ={closeModal} courses = {courses}/>)}
          
          {eventInfo.isModalOpen && eventInfo.clickedEvent && (
        <ScheduleInformationModal
          isModalOpen={eventInfo.isModalOpen}
          clickedEvent={eventInfo.clickedEvent}
          closeModal={() => setEventInfo({ ...eventInfo, isModalOpen: false })}
          role = {role}
        />
      )}
        </div>
        {/* <Calendar events={events} handleEventClick={handleEventClick} /> */}

        {/* <AssignmentModal
            isOpen={isModalOpen}
            onClose={closeModal}
            onCreateAssignment={handleCreateAssignment}
          /> */}
      </div>
    </>
  );
}

export default SchedulePage;
