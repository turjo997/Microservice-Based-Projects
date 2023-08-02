import React, { useState ,useEffect} from "react";
import Circular from "./Circular";
import { Button } from "reactstrap";

import base_url from "../api/bootapi";
import axios from "axios";
import { toast } from "react-toastify";

const AllCirculars=()=>{

    useEffect(()=>{
    document.title="All Circulars";
},[]);

//function to call server:
const getAllCircularsFromServer=()=>{
    const token = localStorage.getItem('token');
    axios.get(`${base_url}/admin/circular/all`, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      }).then(
        (response)=>{
        //success
        console.log(response.data);
        toast.success("Circular has been loaded");
        setCirculars(response.data);

    },
    (error)=>{
        //for error
        console.log(error);
        toast.error("Something went wrong");
    }
    );
};

useEffect(()=>{
    getAllCircularsFromServer();
},[]);

    const [circulars,setCirculars]=useState([])

    const updateCirculars=(circularId)=>{
        setCirculars(circulars.filter((c)=>c.circularId!==circularId));
    };
    return(
        <div>
            <h1>All Circular</h1>
            <p>List of circulars are as follows</p>
            {
                circulars.length>0? circulars.map((item)=> <Circular key={item.circularIdid} circular={item} update={updateCirculars}/>
                ): "No Circulars"
            }
        </div>

    )
}

export default AllCirculars;