import React, { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import { useDispatch, useSelector } from 'react-redux';
import { getCurrentUser } from '../redux/authRedux/authActions';

const ChatPage = () => {
  const [message, setMessage] = useState('');
  const [receivedMessage, setReceivedMessage] = useState([]);
  const [socket, setSocket] = useState(null);
  const [chatMessages, setChatMessages] = useState([]);
  // const email = localStorage.getItem('email');
 
  // console.log(email)

  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
  };

  const dispatch = useDispatch();
  
  useEffect(() => {
    dispatch(getCurrentUser());
    const token = localStorage.getItem('token');
    
    const newSocket = new WebSocket(`ws://localhost:8081/socket?token=${encodeURIComponent(token)}`);

    newSocket.onopen = () => {
      console.log('Connected to server');
      setSocket(newSocket);
    };

    newSocket.onclose = () => {
      console.log('Disconnected from server');
      setSocket(null);
    };

    newSocket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log("Parsed ", data);
      console.log('Received message:', data);
      const {email, text} = data;
  // Create a new chat message object
  const newChatMessage = {
    email: email,
    text: text,
    type: 'received', // Assuming this is the received type
  };

  setReceivedMessage((prevMessages)=>[...prevMessages, newChatMessage]);
  // setChatMessages((prevMessages) => [...prevMessages, newChatMessage]);
    };
    

    return () => {
      newSocket.close();
    };
  }, []);
  const currentUser = useSelector((state)=>state.auth.user);
  console.log(currentUser);
  const handleMessageChange = (event) => {
    setMessage(event.target.value);
  };

  const handleSendMessage = () => {
    if (socket && socket.readyState === WebSocket.OPEN) {
      const messageData = {
        content: message,
      };
      socket.send(JSON.stringify(messageData));
      setChatMessages((prevMessages) => [
        ...prevMessages,
        { text: message, type: 'sent' }, // Include the sent message in the chatMessages state
      ]);
      setMessage('');
    } else {
      console.log('WebSocket is not open.');
    }
  };
  
  return (
    <>
      <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
      <div>
        <h2 style={{display:'flex', flexDirection:'column', marginLeft:'150px',marginTop:'120px'}}>Chat App</h2>
        <div style={{ display: 'flex', flexDirection: 'column', height: '100vh', marginLeft:'150px', height:'500px',width:'600px' }}>
          <div style={{ flex: 1, overflowY: 'scroll', border: '1px solid black', padding: '10px' }}>
            {receivedMessage.map((chatMessage, index) => (
              <div
                key={index}
                style={{
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: chatMessage.email === currentUser ? 'flex-end' : 'flex-start',
                  margin: '5px',
                }}
              >
                <strong>{chatMessage.email}:</strong>
                <div
                  style={{
                    backgroundColor: chatMessage.email === currentUser ? '#e0e0e0' : '#f0f0f0',
                    padding: '8px',
                    borderRadius: '8px',
                    maxWidth: '70%',
                  }}
                >
                  {chatMessage.text}
                </div>
              </div>
            ))}
          </div>
          <div style={{ display: 'flex', alignItems: 'center', padding: '10px' }}>
            <input
              type="text"
              value={message}
              onChange={handleMessageChange}
              style={{ flex: 1, marginRight: '10px' }}
            />
            <button onClick={handleSendMessage}>Send</button>
          </div>
        </div>
      </div>
    </>
  );

};
export default ChatPage;
