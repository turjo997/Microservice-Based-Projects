import React,{useState} from 'react'
import Sidebar from '../components/Sidebar'
import BatchTraineeCard from '../components/batchComponents/BatchTraineeCard'

export default function BatchTrainees() {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
      };
    return (
        <div>
            <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
            {/* <div className= "container" style={{justifyContent:'center', marginTop:'70px', display:'flex'}}>
                <ButtoNModalForm />
            </div> */}
            <div><BatchTraineeCard/></div>
        </div>
      )
}
