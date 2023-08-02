import React, { useState, useEffect } from "react";
import base_url from "../api/bootapi";
import axios from "axios";
import { toast } from "react-toastify";
import ApplicantCircular from "./ApplicantCircular";

const ApplicantAllCircular = () => {
  const [circulars, setCirculars] = useState([]);

  useEffect(() => {
    document.title = "All Circulars";
    getAllCircularsFromServer();
  }, []);

  const getAllCircularsFromServer = () => {
    const token = localStorage.getItem('token');
    axios
      .get(`${base_url}/applicant/circular/all`, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      })
      .then((response) => {
        // Success
        console.log(response.data);
        toast.success("Circulars have been loaded");
        setCirculars(response.data);
      })
      .catch((error) => {
        // Error
        console.log(error);
        toast.error("Something went wrong");
      });
  };

  return (
    <div>
      <h1>All Circulars</h1>
      <p>List of circulars:</p>
      {circulars.length > 0 ? (
        circulars.map((item) => < ApplicantCircular key={item.circularId} circular={item} />)
      ) : (
        <p>No Circulars</p>
      )}
    </div>
  );
};

export default ApplicantAllCircular;
