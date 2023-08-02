
const initialState = {
  batches: [],
  error: null,
  successMessage: null,
  successAssignMessage:null,
  trainees: [],
  batchTrainees:[],
};

const batchReducers = (state = initialState, action) => {
  switch (action.type) {
    case "CREATE_SUCCESS":
      return {
        ...state,
        batches: [...state.batches, action.payload],
        successMessage: action.message,
      };
    case "CREATE_BATCH_ERROR":
      return {
        ...state,
        error: action.payload,
      };
    case "GET_BATCH_DATA_SUCCESS":
      return {
        ...state,
        batches: action.payload,
        error: null,
      };
    case "GET_BATCH_DATA_ERROR":
      return {
        ...state,
        error: action.payload,
      };
    case "GET_TRAINEE_DATA_SUCCESS":
      return {
        ...state,
        trainees: action.payload,
        error: null,
      };
    case "GET_TRAINEE_DATA_ERROR":
      return {
        ...state,
        error: action.payload,
      };
      case "ASSIGN_SUCCESS":
      return {
        ...state,
        batchTrainees:[...state.batchTrainees, action.payload],
        successMessage:action.successMessage,
        successAssignMessage:action.successAssignMessage,
        error: null,
      };
    case "SUBMIT_BATCHTRAINEE_ERROR":
      return {
        ...state,
        error: action.payload,
      };
    default:
      return state;
  }
};

export default batchReducers;
