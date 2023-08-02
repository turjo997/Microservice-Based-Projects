import React,{useState, useEffect} from 'react'
import AssignmentForm from '../components/assignmentComponents/AssignmentForm'
import Sidebar from '../components/Sidebar'
import AssignmentTable from '../components/assignmentComponents/AssignmentTable';
import { useDispatch, useSelector } from 'react-redux';
import { getAllAssignments } from '../redux/assignmentRedux/assignmentActions';
import { getUserRole } from '../redux/authRedux/authActions';

export default function() {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
      };
    useEffect(()=>{
      dispatch(getUserRole());
      dispatch(getAllAssignments());
    },[]);
    const assignments = useSelector((state)=>state.assignment.assignments);
    const role = useSelector((state)=>state.auth.role);

    const dispatch = useDispatch();
    
    console.log(assignments);
    
  return (
    <div className="app-container">
        <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
    {(role==="ADMIN" || role==="TRAINER") && (<AssignmentForm role={role}/>)}
    <AssignmentTable assignments = {assignments} role={role}/>
    </div>
  )
}
