import React from 'react'
import {Navigate, Outlet} from 'react-router-dom'

function TraineeRoute() {
  // Retrieve user role from your authentication logic
  const user = JSON.parse(localStorage.getItem("user"));
  const isTrainer = user && user.role === "TRAINEE";
  return isTrainer ? <Outlet /> : <Navigate to="/signin" />;

}

export default TraineeRoute;