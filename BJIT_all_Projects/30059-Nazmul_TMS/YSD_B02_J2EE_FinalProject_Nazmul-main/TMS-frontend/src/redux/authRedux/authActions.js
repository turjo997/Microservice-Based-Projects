export const login = (email, password) => async(dispatch)=>{
    try{
        const response = await fetch('http://localhost:8081/api/v2/login',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify({email, password}),
        });
        if(response.ok){
            const data = await response.json();
            const {token} =data;
        
            localStorage.setItem('token', 'Bearer ' + token);
            // localStorage.setItem('email', email);
            dispatch({type:'LOGIN_SUCCESS', payload:{token, email}});
        }else{
            const message = await response.text();
            console.log("Error response,", message);
            dispatch({type:'LOGIN_FAILURE',  payload:message})
        }
    } catch(error){
        dispatch({ type: 'LOGIN_FAILURE'});
    }
};


export const getCurrentUser = () =>{
    const token = localStorage.getItem('token');
    return async(dispatch)=>{
        try{
            const response = await fetch('http://localhost:8081/api/user/get-current-user',{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                },
            })
            if (response.ok){
                const user = await response.text();
                dispatch({type:'GET_USER_SUCCESS', payload:user});
            } else {
                const errorData = await response.text();
                console.log(errorData);
                dispatch({type:'GET_USER_FAILURE', payload:errorData});
            }
        } catch(error){
            dispatch({type:"GET_USER_FAILURE"});
        }
    };
};


export const getUserRole = () =>{
    const token = localStorage.getItem('token');
    return async(dispatch)=>{
        try{
            const response = await fetch('http://localhost:8081/api/user/get-user-role',{
                method:'GET',
                headers:{
                    Authorization:`${token}`,
                },
            })
            if (response.ok){
                const role = await response.text();
                dispatch({type:'GET_ROLE_SUCCESS', payload:role});
            } else {
                const errorData = await response.text();
                console.log(errorData);
                dispatch({type:'GET_ROLE_FAILURE', payload:errorData});
            }
        } catch(error){
            dispatch({type:"GET_ROLE_FAILURE"});
        }
    };
};


export const logout = () => {
    return (dispatch) => {
        // Remove token and email from local storage
        localStorage.removeItem('token');
        localStorage.removeItem('email');
        localStorage.removeItem('role');
    
        // Dispatch the logout action
        dispatch({ type: 'LOGOUT' });
      };
};