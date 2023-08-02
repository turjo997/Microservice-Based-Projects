import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getAllSchedules, getAllTrainerSchedules } from '../../redux/scheduleRedux/scheduleActions';
import { createAssignment } from '../../redux/assignmentRedux/assignmentActions';

const AssignmentForm = ({role}) => {
  const [assignmentTitle, setAssignmentTitle] = useState('');
  const [file, setFile] = useState(null);
  const [endTime, setEndTime] = useState('');
  // const [formValues, setFormValues] = useState({});
  const [selectedSchedule, setSelectedSchedule] = useState('');
  const dispatch = useDispatch();
  const schedules = useSelector((state) => state.schedule.schedules);
  const successMessage = useSelector((state)=>state.assignment.successMessage);
  
  useEffect(() => {
    if (role === "ADMIN") {
      dispatch(getAllSchedules())
    }
    else if (role === "TRAINER") {
      dispatch(getAllTrainerSchedules())
    }

  }, []);
  // console.log(schedules);
  // const handleChange = (e) => {
  //   const { name, value } = e.target;
  //   setFormValues((prevFormValues) => ({
  //     ...prevFormValues,
  //     [name]: value,
  //   }));
  // };
  // Function to handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    // Submit the form data to the backend or handle it as needed
    // const selectedScheduleObj = schedules.find((schedule) => schedule.id === selectedSchedule);

    const newAssignment = {
      assignmentTitle,
      assignmentFile: file,
      deadline: endTime,
      scheduleId: Number(selectedSchedule),
    };
    console.log(newAssignment);
    const formData = new FormData();
    formData.append("assignmentTitle", newAssignment.assignmentTitle);
    formData.append("assignmentFile", newAssignment.assignmentFile);
    formData.append("endTime", newAssignment.deadline);
    formData.append("scheduleId", newAssignment.scheduleId);

    dispatch(createAssignment(formData));

    setAssignmentTitle('');
    setEndTime('');
    setFile('');
    setSelectedSchedule('');
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginLeft: '100px', marginTop: '100px', width: '250px' }}>
      <div>
        <label htmlFor="assignmentTitle">Assignment Title:</label>
        <input
          type="text"
          id="assignmentTitle"
          value={assignmentTitle}
          onChange={(e) => setAssignmentTitle(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="file">Upload File:</label>
        <input
          type="file"
          id="file"
          onChange={(e) => setFile(e.target.files[0])}
          required
        />
      </div>
      <div>
        <label htmlFor="endTime">End Time:</label>
        <input
          type="datetime-local"
          id="endTime"
          value={endTime}
          onChange={(e) => setEndTime(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="schedule">Select a Schedule:</label>
        <select
          id="schedule"
          value={selectedSchedule}
          onChange={(e) => {
            const scheduleId = e.target.value;
            setSelectedSchedule(scheduleId); // Store the scheduleId directly
          }}
          required
        >
          <option value="">Select a Schedule</option>
          {schedules.map((schedule) => (
            <option key={schedule.id} value={schedule.id}>
              {schedule.scheduleName}
            </option>
          ))}
        </select>
      </div>
      <button type="submit">Add Assignment</button>
      {successMessage &&(<p style={{color:'green', display:'flex'}}>{successMessage}</p>)}
    </form>
  );
};

export default AssignmentForm;
