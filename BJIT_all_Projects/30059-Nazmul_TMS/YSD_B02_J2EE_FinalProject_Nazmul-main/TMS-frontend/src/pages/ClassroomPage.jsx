import React, { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faReply,
  faArrowAltCircleRight,
  faArrowAltCircleLeft,
} from "@fortawesome/free-solid-svg-icons";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getAllAttachments, getNotices, postNotice, submitClassroomPost, submitReply } from "../redux/classroomRedux/classroomAction";
import ReactModal from "react-modal";
import { getUserRole } from "../redux/authRedux/authActions";
library.add(faReply, faArrowAltCircleRight, faArrowAltCircleLeft);

const ClassroomPage = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [replyText, setReplyText] = useState("");
  const [postText, setPostText] = useState("");
  const [file, setFile] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newNotice, setNewNotice] = useState("");

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };
  const { classroomId } = useParams();
  const [replyTexts, setReplyTexts] = useState({});

  // Update the handleInputChange function to handle changes for each attachment
  const handleInputChange = (event, classroomAttachmentId) => {
    const { value } = event.target;
    setReplyTexts((prevReplyTexts) => ({
      ...prevReplyTexts,
      [classroomAttachmentId]: value,
    }));
  };
  const dispatch = useDispatch();
  const attachments = useSelector((state) => state.classroom.attachments);
  const notices = useSelector((state)=>state.classroom.notices);
  const error = useSelector((state)=>state.classroom.error);

  useEffect(() => {
    dispatch(getUserRole());
    dispatch(getAllAttachments(Number(classroomId)));
    dispatch(getNotices(Number(classroomId)));
  }, [dispatch]);

  const role = useSelector((state)=>state.auth.role);
  const handlePostInputChange = (event) => {
    setPostText(event.target.value);
  };

  
  const handleReplySubmit = (e, classroomAttachmentId) => {
    e.preventDefault();
    const selectedReplyText = replyTexts[classroomAttachmentId] || "";
    const newReply = {
      reply: selectedReplyText,
      attachmentId: Number(classroomAttachmentId),
    };
    dispatch(submitReply(newReply));
    console.log(newReply);
    
  };
  const handlePostSubmit = (e) =>{
    e.preventDefault();

    const formData = new FormData();
    formData.append('message', postText);
    formData.append('classroomId', Number(classroomId));
    if (file) {
      formData.append('fileName', file);
    }
    dispatch(submitClassroomPost(formData));
  }

  const handleNoticeSubmit = (e) =>{
    e.preventDefault();
    const submitNotice = {
      notice: newNotice,
      classroomId: Number(classroomId),
    };
    dispatch(postNotice(submitNotice));
    setNewNotice("");
  }

  const sortAttachmentsByTimestamp = (attachments) => {
    return attachments.sort((a, b) => new Date(b.timeStamp) - new Date(a.timeStamp));
  };

  return (
    <>
      <Sidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
      <MainContent isSidebarOpen={isSidebarOpen}>
        <ContentContainer>
          
          {(role==="TRAINER"||role==="ADMIN")&&(<PostCreationBlock>
            <form onSubmit={handlePostSubmit}>
              <textarea
                placeholder="Enter your post content..."
                value={postText}
                onChange={handlePostInputChange}
              />
              <input
                type="text"
                value={classroomId}
                placeholder="Classroom ID"
                readOnly
              />
              <input type="file" onChange={(e) => setFile(e.target.files[0])} id="file"/>
              <button style={{ marginBottom: "12px", marginRight: "5px" }}>
                Submit {/* Font Awesome reply icon */}
              </button>
            </form>
          </PostCreationBlock>)}

          <CardsContainer>
            {Array.isArray(attachments) && sortAttachmentsByTimestamp(attachments).map((classroom) => (
              <Card key={classroom.classroomName}>
                <div className="timestamp">{classroom.timeStamp}</div>

                <p>
                  <b>Posted by: </b>
                  {classroom.trainerEmail}
                </p>
                <p>
                  <b>Message: </b>
                  {classroom.classroomComment}
                </p>
                {classroom.classroomFile && (
                  <p>
                    <b>Attached File:</b>{" "}
                    <a href={`http://localhost:8081/api/assignment/files/${encodeURIComponent(classroom.classroomFile)}`}
                    download={classroom.classroomFile}>
                      {classroom.classroomFile}
                    </a>
                  </p>
                )}
                <b>Replies:</b>
                {classroom.classroomReply.map((reply) => (
                  <div className="reply">
                    <div className="replyImage">
                      <img
                        src={`http://localhost:8081/api/user/images/${reply.imageFile}`}
                        alt="User"
                      />
                    </div>
                    <div className="replyText">
                      <p>{reply.reply}</p>
                      <span>Replied by: {reply.userEmail}</span>
                    </div>
                  </div>
                ))}

                <div className="replyBox">
                  <form onSubmit={(e) => handleReplySubmit(e, classroom.classroomAttachmentId)}>
                    <input
                      key ={classroom.classroomAttachmentId} 
                      type="text"
                      placeholder="Type your reply..."
                      value={replyTexts[classroom.classroomAttachmentId] || ""}
                      onChange={(e) => handleInputChange(e, classroom.classroomAttachmentId)}
                    />
                    <button style={{ marginLeft: "5px" }}>
                      <FontAwesomeIcon icon={faReply} />
                      Reply
                    </button>
                  </form>
                </div>
              </Card>
            ))}
            {error && <div style={{display:'flex', justifyContent:'center', marginTop:'200px',marginLeft:'50px',fontSize:'30px'}}>
              {error}
            </div>}
          </CardsContainer>
          <Divider />
          <NoticesContainer>
            {notices.map((notice) => (
              <Notice>
                <p>{notice.notice}</p>
              </Notice>
            ))}
            {(role==="TRAINER"||role==="ADMIN")&&(<button onClick={handleOpenModal}>Create Notice</button>)}
          </NoticesContainer>
          {(role==="TRAINER"||role==="ADMIN")&&(<ModalOverlay isOpen={isModalOpen} onRequestClose={handleCloseModal}>
      <ModalContent>
        <ModalTitle>Create Notice</ModalTitle>
        <Form onSubmit={handleNoticeSubmit}>
          <FormLabel>
            Notice:
            <FormInput
              type="text"
              name="notice"
              value={newNotice}
              onChange={(e)=>setNewNotice(e.target.value)}
            />
          </FormLabel>
          <FormLabel>
            Classroom ID:
            <FormInput
              name="classroomId"
              value={classroomId}
              onChange={handleInputChange}
              readOnly
            />
          </FormLabel>
          <ButtonContainer>
            <Button primary type="submit">Create</Button>
            <Button type="button" onClick={handleCloseModal}>
              Cancel
            </Button>
          </ButtonContainer>
        </Form>
      </ModalContent>
    </ModalOverlay>)}
        </ContentContainer>
      </MainContent>
    </>
  );
};
const MainContent = styled.div`
  padding-left: ${(props) => (props.isSidebarOpen ? "250px" : "0")};
  transition: padding-left 0.3s ease-in-out;
  margin-top: 80px;
`;

const CardsContainer = styled.div`
  flex: 1; /* Takes the remaining available space */
  display: flex;
  flex-direction: column;

  margin-bottom: 20px;
`;
const Card = styled.div`
  border: 1px solid #ccc;
  padding: 15px;
  border-radius: 8px;
  width: 600px;
  margin: 10px auto; 
  margin-bottom:20px;

  .timestamp {
    color: #aaa;
    font-size: 12px;
    text-align: right;
  }

  h3 {
    font-size: 20px;
    margin-bottom: 10px;
  }

  p {
    margin-bottom: 10px;
  }

  .reply {
    display: flex;
    align-items: center;
    margin: 10px 0;

    .replyImage {
      width: 30px;
      height: 30px;
      border-radius: 50%;
      overflow: hidden;
      margin-right: 10px;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }

    .replyText {
      flex: 1;
      display: flex;
      flex-direction: column;

      p {
        margin-bottom: 5px;
      }

      span {
        color: #aaa;
        font-size: 12px;
      }
    }
  }

  .replyBox {
    display: flex;
    align-items: center;
    margin-top: 10px;

    input {
      flex: 1;
      height: 30px;
      border: 1px solid #ccc;
      border-radius: 4px;
      padding: 5px;
      margin-right: 10px;
    }

    button {
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 5px 10px;
      border: none;
      border-radius: 4px;
      background-color: #007bff;
      color: #fff;
      cursor: pointer;
    }

    i {
      margin-right: 5px;
    }`;
const ContentContainer = styled.div`
  display: flex;
  align-items: flex-start; /* Align content to the top */
  margin: 0 auto;
  max-width: 1200px; /* Adjust the max-width as needed */
`;
const Divider = styled.div`
  display: flex;
  width: 1px;
  background-color: #ccc;
  margin: 0 20px; /* Add some space on both sides of the divider */
`;
const NoticesContainer = styled.div`
  flex-shrink: 0; /* Prevent notices container from shrinking */
  width: 300px; /* Set the width of the notices container */
  background-color: #f5f5f5; /* Add background color */
  padding: 20px; /* Add padding */
  border-radius: 8px;
`;

const Notice = styled.div`
  margin-bottom: 20px; /* Add space between individual notices */
  border: 1px solid #ccc; /* Add border */
  border-radius: 8px;
  padding: 10px; /* Add padding */
  border-radius: 8px; /* Add border radius */
`;
const PostCreationBlock = styled.div`
  /* Add your styles for the post creation block here */
  background-color: #f9f9f9;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 20px;
  margin-right: 10px;
  margin-top: 10px;

  textarea {
    width: 100%;
    height: 100px;
    margin-bottom: 10px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }

  input[type="text"] {
    width: 100%;
    margin-bottom: 10px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
`;
const ModalOverlay = styled(ReactModal)`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
`;

// Styles for the modal content
const ModalContent = styled.div`
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  max-width: 400px;
  width: 90%;
`;

const ModalTitle = styled.h2`
  font-size: 24px;
  margin-bottom: 16px;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const FormLabel = styled.label`
  font-weight: bold;
  margin-bottom: 8px;
`;

const FormInput = styled.input`
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 16px;
`;

const FormTextArea = styled.textarea`
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
  height: 120px;
  margin-bottom: 16px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
`;

const Button = styled.button`
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  margin-left: 8px;
  cursor: pointer;
  background-color: ${(props) => (props.primary ? "#007bff" : "#ccc")};
  color: #fff;
`;
export default ClassroomPage;

//   return (
//
//   );
// };
