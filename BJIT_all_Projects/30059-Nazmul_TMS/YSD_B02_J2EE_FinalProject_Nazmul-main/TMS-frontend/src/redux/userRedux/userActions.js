const token = localStorage.getItem('token');
export const getUserProfile = () =>{
    // const email = localStorage.getItem('email');
    return async(dispatch)=>{
        try{
            const response = await fetch('http://localhost:8081/api/user/get-user-profile',{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
            });
            if(response.ok){
                const data =  await response.json();
                dispatch({type:'USER_PROFILE_GET_SUCCESS',payload:data});
            } else {
                const errorData = await response.text();
                dispatch({type:'GET_ERROR', payload:errorData});
            }
        } catch(error){
            console.error(error);
            const errorData = error.message;
            dispatch({type:'GET_ERROR', payload:errorData});
        }
    }
}

export const updatProfile = (formData) =>{
    // const email = localStorage.getItem('email');
    return async(dispatch)=>{
        try{
            const response = await fetch(`http://localhost:8081/api/user/update-profile`,{
                method:'PUT',
                headers:{
                    Authorization:`${token}`,
                },
                body:formData,
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'UPDATE_SUCCESS',payload:data, successMessage:"Updated"});
            }else{
                const errorData = await response.text();
                console.log(errorData);
                dispatch({type:'UPDATE_ERROR', payload:errorData});
            }
        } catch(error){
            console.error(error);
            dispatch({type:'UPDATE_ERROR'});
        }
    }
}