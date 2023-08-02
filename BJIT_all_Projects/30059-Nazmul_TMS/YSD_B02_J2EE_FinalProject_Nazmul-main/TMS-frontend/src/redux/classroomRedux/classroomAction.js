const token = localStorage.getItem('token');
export const getClassrooms = () =>{
    // const email = localStorage.getItem('email');
    return async(dispatch) => {
        try{
            const response = await fetch('http://localhost:8081/api/classroom/get-assigned-classroom',{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'GET_CLASSROOM_SUCCESS', payload:data});
            } else {
                const errorData = await response.text();
                dispatch({type:'GET_CLASSROOM_FAILURE', payload:errorData});
            }
        } catch(error){
            console.error(error);
            dispatch({type:'GET_CLASSROOM_FAILURE'});
        }
    }
}

export const getAllAttachments = (classroomId) => {
    return async(dispatch) => {
        try{
            const response = await fetch(`http://localhost:8081/api/classroom/get-classroom-details/${classroomId}`,{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'GET_ATTACHMENT_SUCCESS', payload:data});
            } else {
                const error = await response.text();
                console.log(error);
                dispatch({type:'GET_ATTACHMENT_FAILURE', payload:error});
            }
        } catch(error){
            dispatch({type:'GET_ATTACHMENT_FAILURE'})
        }
    }
}

export const submitReply = (newReply) =>{
    return async(dispatch)=>{
        console.log("IN action:", newReply);
        try{
            const response = await fetch('http://localhost:8081/api/classroom/create-reply',{
                method:'POST',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
                body:JSON.stringify(newReply),
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'REPLY_SUCCESS', payload:data, successMessage:"Reply is successful"});
            } else{
                const errorData = await response.text();
                console.log(errorData);
                dispatch({type:'REPLY_FAILURE', payload:errorData});
            }
        } catch(error){
            console.error(error);
            dispatch({type:'REPLY_FAILURE'});
        }
    }
}

export const submitClassroomPost= (formData) =>{
    return async(dispatch)=>{
        try{
            const response = await fetch('http://localhost:8081/api/classroom/create-attachments',{
                method:'POST',
                headers:{
                    Authorization:`${token}`
                },
                body:formData,
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'POST_SUCCESS', payload:data, successMessage:"attachment added"});
            } else {
                const errorData = await response.text();
                console.log(errorData);
                dispatch({type:'POST_FAILURE', payload:errorData, message:"Failed to add post"});
            }
        }catch(error){
            dispatch({type:'POST_FAILURE'});
        }
    }
}

export const postNotice = (submitNotice) =>{
    return async(dispatch)=>{
        try{

        
        const response = await fetch('http://localhost:8081/api/classroom/create-notice',{
            method:'POST',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                },
            body:JSON.stringify(submitNotice),
        });
        if(response.ok){
            const data = await response.json();
            console.log(data);
            dispatch({type:'NOTICE_CREATE_SUCCESS', payload:data, successMessage:"Notice created"});
        } else {
            const errorData = await response.text();
            console.log(errorData);
            dispatch({type:'NOTICE_CREATE_ERROR', payload:errorData});
        }
    } catch(error){
        dispatch({type:'NOTICE_CREATE_ERROR'});
    }
    }
}

export const getNotices = (classroomId) =>{
    return async(dispatch) =>{
        try{
            const response = await fetch(`http://localhost:8081/api/classroom/get-classroom-notices/${classroomId}`,{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                    'Content-Type':'application/json',
                }
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
                dispatch({type:'GET_NOTICE_SUCCESS',payload:data});
            } else{
                const errorData = await response.text();
                dispatch({type:'NOTICE_GET_ERROR', payload:errorData});
            }
        }catch(error){
            console.error(error);
            dispatch({type:'NOTICE_GET_ERROR'});
        }
    }
}