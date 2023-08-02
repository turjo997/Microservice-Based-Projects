const initialState = {
    schedules: [],
    error: null,
    trainers:[],
  };
  
  const scheduleReducer = (state = initialState, action) => {
    switch (action.type) {
      case "ADD_SCHEDULE_SUCCESS":
        return {
          ...state,
          schedules: [...state.schedules, action.payload],
          message:action.message
        };
        case "CREATE_SCHEDULE_ERROR":
          return{
            ...state,
            error:action.payload,
          };
          case "GET_TRAINER_DATA_SUCCESS":
      return {
        ...state,
        trainers: action.payload,
        error: null,
      };
    case "GET_TRAINER_DATA_ERROR":
      return {
        ...state,
        error: action.payload,
      };
      case "GET_SCHEDULES_SUCCESS":
        return {
          ...state,
          schedules:action.payload,
          error: null,
        };
        case "GET_SCHEDULE_FAILURE":
          return {
            ...state,
            error:action.payload,
          }
      default:
        return state;
    }
  };
  
  export default scheduleReducer;