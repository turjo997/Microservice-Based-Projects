const initialState = {
    assignments:[],
    assignmentSubmissions:[],
    error:null,
    successMessage:null,
};
const assignmentReducer = (state=initialState, action)=>{
    switch(action.type){
        case "ASSIGNMENT_ADD_SUCCESS":
            return {
                ...state,
                assignments:[...state.assignments, action.payload],
                error:null,
                successMessage:action.message,
            };
        case "CREATION_ERROR":
            return {
                ...state,
                error:action.payload,
            };
        case "GET_ASSIGNMENT_SUCCESS":
            return {
                ...state,
                assignments: action.payload,
                error:null,
            };
        case "GET_ASSIGNMENT_FAILURE":
            return {
                ...state,
                error:action.payload,
            };
        case "GET_SUBMISSIONS_SUCCESS":
            return{
                ...state,
                assignmentSubmissions:action.payload,
                error:null,
            };
        case "GET_SUBMISSIONS_FAILURE":
            return {
                ...state,
                error: action.payload,
            };
        case "SUBMISSION_SUCCESS":
            return{
                ...state,
                assignmentSubmissions:[...state.assignmentSubmissions, action.payload],
                successMessage:action.successMessage,
                error:null,
            };
        case "SUBMISSION_FAILURE":
            return{
                ...state,
                error: action.payload,
            }
        default:
            return state;
    }
}
export default assignmentReducer;