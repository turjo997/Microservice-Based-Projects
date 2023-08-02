const initialState = {
    token:null,
    email:null,
    error:null,
    role:null,
    user:null,
};
const authReducer = (state = initialState, action)=>{
    switch(action.type){
        case 'LOGIN_SUCCESS':
            return {
                ...state,
                token: action.payload.token,
                email: action.payload.email,
            }
        case 'LOGIN_FAILURE':
            return {...state, token: null, error: action.payload };
        case 'GET_USER_SUCCESS':
            return{
                ...state,
                user:action.payload,
                error:null,
            }
        case 'GET_USER_FAILURE':
            return{
                ...state,
                error:action.payload,
            }
        case 'GET_ROLE_SUCCESS':
            return {
                ...state,
                role:action.payload,
                error:null,
            };
        case 'GET_ROLE_FAILURE':
            return {
                ...state,
                role:null,
                error:action.payload,
            };
        case 'LOGOUT':
            return{
                ...state,
                token: null,
                email:null,
            };
        default:
            return state;
    }
};
export default authReducer;
