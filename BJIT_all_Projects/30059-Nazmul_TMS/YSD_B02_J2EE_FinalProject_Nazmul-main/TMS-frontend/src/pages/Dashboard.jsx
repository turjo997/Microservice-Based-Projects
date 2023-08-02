import React,{useEffect, useState} from 'react'
import { useDispatch, useSelector } from 'react-redux';
import Sidebar from '../components/Sidebar';
import UserProfileCard from '../components/userComponents/UserProfileCard';
import UserEditModal from '../components/userComponents/UserEditModal';
import { getUserProfile } from '../redux/userRedux/userActions';
import { getUserRole } from '../redux/authRedux/authActions';

function Dashboard() {
  const token  = useSelector((state) => state.auth.token);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  
  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
};
const dispatch = useDispatch();
const user = useSelector((state)=>state.user.profileInfo);

useEffect(()=>{
  dispatch(getUserRole());
  dispatch(getUserProfile());
},[]);

console.log(user);
const role = useSelector((state)=>state.auth.role);
const [profile, setProfile] = React.useState({
  // Default profile data here
});



const [isEditing, setIsEditing] = useState(false);

  const handleEditProfile = () => {
    setIsEditing(true);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
  };
  return (
    <div>
    <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar}/>
    <div>
      {isEditing ? (
        <UserEditModal
          profile={user}
          onCancel={handleCancelEdit}
          role = {role}
        />
      ) : (
        <UserProfileCard profile={user} role={role} onEdit={handleEditProfile} />
      )}
    </div>
    </div>
    
  )
}

export default Dashboard