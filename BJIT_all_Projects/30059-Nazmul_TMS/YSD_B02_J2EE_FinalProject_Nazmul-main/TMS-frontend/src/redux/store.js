import { createStore, applyMiddleware, combineReducers} from 'redux';
import authReducer from './authRedux/authReducer';
import batchReducers from './batchRedux/batchReducers';
import thunk from 'redux-thunk'
import scheduleReducer from './scheduleRedux/scheduleReducers';
import assignmentReducer from './assignmentRedux/assignmentReducers';
import classroomReducers from './classroomRedux/classroomReducers';
import userReducers from './userRedux/userReducers';

const rootReducer = combineReducers({
    auth: authReducer,
    batch: batchReducers,
    schedule:scheduleReducer,
    assignment:assignmentReducer,
    classroom:classroomReducers,
    user:userReducers,
});

const store = createStore(rootReducer, applyMiddleware(thunk));
export default store;



// const classroomData = [
//     {
    //   classroomName: 'Mathematics',
    //   classroomComment: 'This is the main comment of the Mathematics classroom.',
    //   classroomFile: 'file1.docx',
    //   classroomReply: [
    //     {
//           replyId: 1,
//           replyText: 'Reply 1 for Mathematics',
//           replyAuthor: 'user1@example.com',
//           replyImage: 'user1.jpg',
//         },
//         {
//           replyId: 2,
//           replyText: 'Reply 2 for Mathematics',
//           replyAuthor: 'user2@example.com',
//           replyImage: 'user2.jpg',
//         },
//       ],
//       timeStamp: '2023-07-19 12:34 PM',
//     },
//     {
//       classroomName: 'Science',
//       classroomComment: 'This is the main comment of the Science classroom.',
//       classroomFile: 'file2.docx',
//       classroomReply: [
//         {
//           replyId: 1,
//           replyText: 'Reply 1 for Science',
//           replyAuthor: 'user3@example.com',
//           replyImage: 'user3.jpg',
//         },
//       ],
//       timeStamp: '2023-07-20 02:15 PM',
//     },
//     // Add more classroom data as needed
//   ];
  