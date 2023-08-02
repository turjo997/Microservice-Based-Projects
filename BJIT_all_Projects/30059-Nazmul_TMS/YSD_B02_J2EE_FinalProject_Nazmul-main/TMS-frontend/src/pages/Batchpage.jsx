import React,{useEffect, useState} from 'react'
import Sidebar from '../components/Sidebar';
import BatchCardList from '../components/batchComponents/BatchCardList';
import ButtoNModalForm from '../components/batchComponents/ButtonModalForm';
import { useDispatch, useSelector } from 'react-redux';
import { getBatchData } from '../redux/batchRedux/batchActions';

export default function Batchpage({role}) {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const batches = useSelector((state)=>state.batch.batches)
    const dispatch = useDispatch();

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
      };
    useEffect(()=>{
      dispatch(getBatchData());
    },[dispatch]);
    
  return (
    <div>
        <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
        <div className= "container" style={{justifyContent:'center', marginTop:'70px', display:'flex'}}>
            <ButtoNModalForm />
        </div>
        <div><BatchCardList batches={batches}/></div>
    </div>
  )
}
