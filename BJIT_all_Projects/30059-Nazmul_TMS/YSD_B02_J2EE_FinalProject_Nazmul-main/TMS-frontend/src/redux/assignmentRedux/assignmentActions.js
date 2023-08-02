const token = localStorage.getItem('token');
export const createAssignment = (formData) =>{
    return async(dispatch)=>{
        try{
            const response = await fetch('http://localhost:8081/api/assignment/create-assignment',{
                method:'POST',
                headers:{
                    Authorization:`${token}`,
                },
                body:formData,
            });
            if(response.ok){
                console.log("ASSIGNMENT ADDED");
                dispatch({type:"ASSIGNMENT_ADD_SUCCESS", payload:response.json(), message:"Assignment added"});
            } else {
                const error= await response.text();
                console.log('Error creating assignment', error);
                dispatch({type:'CREATION_ERROR', payload:error});
            }
        } catch(error){
            console.error('ERROR adding assignment', error);
            dispatch({type:'CREATION_ERROR'})
        }
    };
}

export const getAllAssignments = () =>{
    // const email = localStorage.getItem('email');
    return async(dispatch)=>{
        try{
            const response = await fetch('http://localhost:8081/api/assignment/get-assignments-by-user',{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
            });
            if(response.ok){
                const data = await response.json();
                dispatch({type:'GET_ASSIGNMENT_SUCCESS', payload:data});
            } else {
                const errorData = await response.text();
                dispatch({type:'GET_ASSIGNMENT_FAILURE', payload:errorData});
            }
                
                
        } catch(error){
            dispatch({type:'GET_ASSIGNMENT_FAILURE'});
        }
    };
}

export const getAllSubmissions = (assignmentId) =>{
    return async(dispatch)=>{
        try{
            const response = await fetch(`http://localhost:8081/api/assignment/get-assignment-submissions/${assignmentId}`,{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'GET_SUBMISSIONS_SUCCESS', payload:data});
            } else {
                const errorData = await response.text();
                dispatch({type:'GET_SUBMISSIONS_FAILURE', payload:errorData});
            }
        } catch(error){
            dispatch({type:'GET_SUBMISSIONS_FAILURE'});
        }
    }
}

export const submitAssignmentAnswer = (formData) =>{
    return async(dispatch)=>{
        try{
            const response =  await fetch('http://localhost:8081/api/assignment/submit-answer',{
                method:'POST',
                headers:{
                    Authorization:`${token}`,
                },
                body:formData,
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:"SUBMISSION_SUCCESS", payload:data, successMessage:'Submitted successfully'});
            } else {
                const errorData = await response.text();
                dispatch({type:"SUBMISSION_FAILURE", payload:errorData});
            }
        } catch(error){
            dispatch({type:"SUBMISSION_FAILURE"});
        }
    }
}