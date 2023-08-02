import React, { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import ClassroomCardList from '../components/classroomComponents/ClassroomCardList';
import { useDispatch, useSelector } from 'react-redux';
import { getClassrooms } from '../redux/classroomRedux/classroomAction';

const Classroom = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const dispatch = useDispatch();
  const classrooms = useSelector((state)=>state.classroom.classrooms);
  
  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
};
// const classroomData = [
//   { id: 1, title: 'Mathematics' },
//   { id: 2, title: 'Science' },
//   { id: 3, title: 'History' },
//   // Add more classroom titles here
// ];
useEffect(()=>{
  dispatch(getClassrooms());
},[]);

console.log("MAIN PAGE: ", classrooms);
  return (
    <div>
    <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar}/>
    <ClassroomCardList  classrooms = {classrooms}/>
    </div>
    
  )

}  

export default Classroom;





// const [postText, setPostText] = useState('');
//   const [postFile, setPostFile] = useState(null);
//   const [posts, setPosts] = useState([]);

//   const handlePostTextChange = (e) => {
//     setPostText(e.target.value);
//   };

//   const handlePostFileChange = (e) => {
//     setPostFile(e.target.files[0]);
//   };

//   const handleSubmit = () => {
//     if (postText || postFile) {
//       const newPost = {
//         text: postText,
//         file: postFile,
//         date: new Date().toLocaleString(),
//       };
//       setPosts([newPost, ...posts]);
//       setPostText('');
//       setPostFile(null);
//     }
//   };

//   return (
//     <div>
//       <h1>Classroom Title</h1>
//       <div>
//         <textarea
//           value={postText}
//           onChange={handlePostTextChange}
//           placeholder="Write your post..."
//           rows={4}
//           cols={50}
//         />
//         <br />
//         <input type="file" onChange={handlePostFileChange} />
//         <button onClick={handleSubmit}>Submit Post</button>
//       </div>
//       <div>
//         {posts.map((post, index) => (
//           <div key={index} style={{ border: '1px solid #ccc', padding: '10px', margin: '10px' }}>
//             <p>{post.text}</p>
//             {post.file && <p>Uploaded File: {post.file.name}</p>}
//             <p>{post.date}</p>
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// };