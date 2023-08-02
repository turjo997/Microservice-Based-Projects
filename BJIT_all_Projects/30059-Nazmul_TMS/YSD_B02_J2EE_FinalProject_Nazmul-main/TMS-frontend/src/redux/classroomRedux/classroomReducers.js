const initialState = {
    classrooms:[],
    error:null,
    attachments:[],
    errorMessage:null,
    successMessage:null,
    reply :[],
    notices:[],
};

const classroomReducers = (state = initialState, action)=>{
    switch(action.type){
        case "GET_CLASSROOM_SUCCESS":
            return {
                ...state,
                classrooms:action.payload,
                error:null,
            };
        case "GET_ATTACHMENT_SUCCESS":
            return {
                ...state,
                attachments:action.payload,
                error:null,
            };
        case "GET_CLASSROOM_FAILURE":
            return {
                ...state,
                error: action.payload,
            };
        case "GET_ATTACHMENT_FAILURE":
            return {
                ...state,
                error: action.payload,
                // errorMessage:action.message,
            };
        case "REPLY_SUCCESS":
            return{
                ...state,
                reply:action.payload,
                successMessage:action.successMessage,
                error:null,
            };
        case "REPLY_FAILURE":
            return{
                ...state,
                error:action.payload,
            };
        case "POST_SUCCESS":
            return{
                ...state,
                attachments:action.payload,
                successMessage:action.successMessage,
                error:null,
            }
        case "POST_FAILURE":
            return{
                ...state,
                error:action.payload,
            }
        case "NOTICE_CREATE_SUCCESS":
            return{
                ...state,
                notices:action.payload,
                error:null,
                successMessage:action.successMessage,
            }
        case "NOTICE_CREATE_ERROR":
            return{
                ...state,
                error:action.payload,
            };
        case "GET_NOTICE_SUCCESS":
            return{
                ...state,
                notices:action.payload,
                error:null,
            };
        case "NOTICE_GET_ERROR":
            return{
                ...state,
                error:action.payload,
            }
        default:
            return state;
    }
}

export default classroomReducers;