const initialState = {
    profileInfo : [],
    error:null,
}
const userReducers = (state = initialState, action)=>{
    switch(action.type){
        case "USER_PROFILE_GET_SUCCESS":
            return{
                ...state,
                profileInfo:action.payload,
                error:null,
            };
        case "GET_ERROR":
            return{
                ...state,
                error:action.payload,
            };
        case "UPDATE_SUCCESS":
            return{
                ...state,
                profileInfo:action.payload,
                error:null,
            };
        case "UPDATE_ERROR":
            return{
                ...state,
                error:action.payload,
            };
        default:
            return state;
    }
}
export default userReducers;