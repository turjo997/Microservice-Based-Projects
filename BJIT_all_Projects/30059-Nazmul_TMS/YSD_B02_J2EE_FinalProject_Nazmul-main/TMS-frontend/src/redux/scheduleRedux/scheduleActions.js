const token = localStorage.getItem('token');
export const createSchedule = (scheduleData) => {
    
    return async (dispatch) => {
      try {
        const response = await fetch('http://localhost:8081/api/schedules/create-schedule', {
          method: 'POST',
          headers: {
            Authorization:`${token}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(scheduleData),
        });
  
        if (response.ok) {
          console.log('Schedule added successfully!');
          const data = await response.json();
          // Optionally, you can perform additional actions after the batch is added
          dispatch({ type: 'ADD_SCHEDULE_SUCCESS', payload: data, message:"Schedule added" });
        } else {
            const errorData = await response.text();
            console.log('Error adding schedule:', errorData);
            dispatch({ type: 'CREATE_SCHEDULE_ERROR', payload: errorData });
        }
      } catch (error) {
        console.error('Error adding Schedule:', error);
        dispatch({ type: 'CREATE_SCHEDULE_ERROR', payload: 'Same Schedule exists' });
      }
    };
  };
  export const getAllSchedules = () =>{
    return async (dispatch)=>{
      try{
        const response = await fetch('http://localhost:8081/api/schedules/get-schedules',{
          method:'GET',
          headers:{
            Authorization:`${token}`,
            'Content-Type': 'application/json',
          },
        });
        if(response.ok){
          const data = await response.json();
          dispatch({type:'GET_SCHEDULES_SUCCESS', payload:data});
        } else {
          const errorData = await response.text();
          dispatch({type:'GET_SCHEDULE_FAILURE', payload:errorData});
        }
      }catch(error){
        dispatch({type:'GET_SCHEDULE_FAILURE'});
      }
    };
  };

  export const getAllTrainerSchedules = () =>{
    // const trainerEmail = localStorage.getItem('email');
    return async (dispatch)=>{
      try{
        const response = await fetch(`http://localhost:8081/api/schedules/get-schedules-by-trainer`,{
          method:'GET',
          headers:{
            Authorization:`${token}`,
            'Content-Type': 'application/json',
          },
        });
        if(response.ok){
          const data = await response.json();
          dispatch({type:'GET_SCHEDULES_SUCCESS', payload:data});
        } else {
          const errorData = await response.text();
          dispatch({type:'GET_SCHEDULE_FAILURE', payload:errorData});
        }
      }catch(error){
        dispatch({type:'GET_SCHEDULE_FAILURE'});
      }
    };
  };
  export const getAllTraineeSchedules = () =>{
    return async (dispatch)=>{
      try{
        const response = await fetch(`http://localhost:8081/api/schedules/get-schedules-by-trainee`,{
          method:'GET',
          headers:{
            Authorization:`${token}`,
            'Content-Type': 'application/json',
          },
        });
        if(response.ok){
          const data = await response.json();
          dispatch({type:'GET_SCHEDULES_SUCCESS', payload:data});
        } else {
          const errorData = await response.text();
          dispatch({type:'GET_SCHEDULE_FAILURE', payload:errorData});
        }
      }catch(error){
        dispatch({type:'GET_SCHEDULE_FAILURE'});
      }
    };
  };

  export const getAllTrainers = (roleName) => {
    return async (dispatch) => {
      try {
        const response = await fetch(`http://localhost:8081/api/user/get-all-trainers/${roleName}`, {
            method: 'GET',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            },}); // Adjust the URL according to your API endpoint
  
        if (response.ok) {
          const data = await response.json();
          dispatch({ type: 'GET_TRAINER_DATA_SUCCESS', payload: data });
        } else {
          const errorData = await response.text()
          console.log('Error fetching trainee data:', errorData);
          dispatch({ type: 'GET_TRAINER_DATA_ERROR', payload: errorData });
        }
      } catch (error) {
        console.error('Error fetching trainee data:', error);
        dispatch({ type: 'GET_TRAINER_DATA_ERROR', payload: 'Failed to user trainee data' });
      }
    };
  };