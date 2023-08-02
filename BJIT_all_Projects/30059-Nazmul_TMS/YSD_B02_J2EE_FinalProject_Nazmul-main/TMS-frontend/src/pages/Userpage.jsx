import React,{useState} from "react";
import Sidebar from "../components/Sidebar";
import UserFormModal from "../components/registrationComponents/UserFormModal";
import Table from "../components/registrationComponents/Table";

function Userpage() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [isFormOpen, setIsFormOpen] = useState(false);

  const openForm = () => {
    setIsFormOpen(true);
  };

  const closeFormModal = () => {
    setIsFormOpen(false);
  };

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };
  return (
    <div>
      <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
      <div className="button-container" style={{display:'flex', gap:'10px', marginLeft:'80px', marginTop:'80px'}}>
        <button onClick={openForm} style={{backgroundColor:'green'}}>Add User</button>
      </div>
      <div><Table /></div>
      
      {isFormOpen && <UserFormModal isOpen={isFormOpen} onClose={closeFormModal} />}
        
    </div>
  );
}
export default Userpage;
