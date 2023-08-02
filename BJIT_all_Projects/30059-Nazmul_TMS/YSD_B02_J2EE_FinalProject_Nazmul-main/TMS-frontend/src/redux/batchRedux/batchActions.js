const token = localStorage.getItem('token');
export const createBatch = (batchData) => {
    
    return async (dispatch) => {
      try {
        const response = await fetch('http://localhost:8081/api/batch/create-batch', {
          method: 'POST',
          headers: {
            Authorization:`${token}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(batchData),
        });
  
        if (response.ok) {
          console.log('Batch added successfully!');
          // Optionally, you can perform additional actions after the batch is added
          dispatch({ type: 'CREATE_SUCCESS', payload: response.json(), message:"Batch added" });
        } else {
            const errorData = await response.json();
            console.log('Error adding batch:', errorData.message);
            dispatch({ type: 'CREATE_BATCH_ERROR', payload: errorData.message });
        }
      } catch (error) {
        console.error('Error adding batch:', error);
        dispatch({ type: 'CREATE_BATCH_ERROR', payload: 'Same batch exists' });
      }
    };
  };

  // Get Batches

export const getBatchData = () => {
    return async (dispatch) => {
      try {
        const response = await fetch('http://localhost:8081/api/batch/get-batches', {
            method: 'GET',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            },}); // Adjust the URL according to your API endpoint
  
        if (response.ok) {
          const data = await response.json();
          dispatch({ type: 'GET_BATCH_DATA_SUCCESS', payload: data });
        } else {
          console.log('Error fetching batch data:', response.status);
          dispatch({ type: 'GET_BATCH_DATA_ERROR', payload: 'Failed to fetch batch data' });
        }
      } catch (error) {
        console.error('Error fetching batch data:', error);
        dispatch({ type: 'GET_BATCH_DATA_ERROR', payload: 'Failed to fetch batch data' });
      }
    };
  };
  
  export const getAllTrainees = (roleName) => {
    return async (dispatch) => {
      try {
        const response = await fetch(`http://localhost:8081/api/user/get-all-trainees/${roleName}`, {
            method: 'GET',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            },}); // Adjust the URL according to your API endpoint
  
        if (response.ok) {
          const data = await response.json();
          dispatch({ type: 'GET_TRAINEE_DATA_SUCCESS', payload: data });
        } else {
          console.log('Error fetching trainee data:', response.status);
          dispatch({ type: 'GET_TRAINEE_DATA_ERROR', payload: 'Failed to fetch trainee data' });
        }
      } catch (error) {
        console.error('Error fetching trainee data:', error);
        dispatch({ type: 'GET_TRAINEE_DATA_ERROR', payload: 'Failed to user trainee data' });
      }
    };
  };

export const submitBatchTrainees = (items) =>{
  return async(dispatch) =>{
    try{
      const response = await fetch('http://localhost:8081/api/batch/assign-trainees-to-batch', {
            method: 'POST',
            headers: {
              Authorization:`${token}`,
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(items),
          });
          if (response.ok) {
            const data = await response.json();
            console.log(data);
            dispatch({ type: 'ASSIGN_SUCCESS', payload: data, successAssignMessage:"Assigned successfully" });
          } else {
            console.log('Error Assigning:', response.status);
            dispatch({ type: 'SUBMIT_BATCHTRAINEE_ERROR', payload: 'Failed to assign trainees' });
          }
    }catch(error){
      console.error("Error submitting batch trainee data", error);
      dispatch({type:"SUBMIT_BATCHTRAINEE_ERROR", payload:"Failed to assign trainees to batches"});
    }
  }
};
  