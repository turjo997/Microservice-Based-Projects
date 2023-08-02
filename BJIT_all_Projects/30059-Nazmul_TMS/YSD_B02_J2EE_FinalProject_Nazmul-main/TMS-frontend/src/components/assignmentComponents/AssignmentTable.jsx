import React, { useEffect, useState } from 'react';
import './AssignmentTable.css';
import AssignmentModal from './AssignmentModal';
import SeeSubmissionsModal from './SeeSubmissionsModal';
import { useDispatch, useSelector } from 'react-redux';
import { getAllSubmissions } from '../../redux/assignmentRedux/assignmentActions';
import SubmitAnswerModal from './SubmitAnswerModal';

const ITEMS_PER_PAGE = 2;
const AssignmentTable = ({ assignments, role }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isSeeSubmissionModalOpen, setIsSeeSubmissionModalOpen] = useState(false);
  const [isSubmitModalOpen, setIsSubmitModalOpen] = useState(false);
  const [selectedAssignment, setSelectedAssignment] = useState(null);
  const [neededId, setNeededId] = useState(null);
  const dispatch = useDispatch();
  const assignmentSubmissions = useSelector((state)=>state.assignment.assignmentSubmissions);

  const totalPages = Math.ceil(Object.values(assignments).length / ITEMS_PER_PAGE);

  // Calculate the index range for the current page
  const indexOfLastSchedule = currentPage * ITEMS_PER_PAGE;
  const indexOfFirstSchedule = indexOfLastSchedule - ITEMS_PER_PAGE;
  const currentSchedules = Object.values(assignments).slice(indexOfFirstSchedule, indexOfLastSchedule);
  
  const handlePaginationChange = (page) => {
    setCurrentPage(page);
  };

  const handlePreviousPage = () => {
    const totalPages = Math.ceil(Object.values(assignments).length / ITEMS_PER_PAGE);
    if (currentPage > 1) {
      setCurrentPage((prevPage) => prevPage - 1);
    } else {
      setCurrentPage(totalPages);
    }
  };

  const handleNextPage = () => {
    const totalPages = Math.ceil(Object.values(assignments).length / ITEMS_PER_PAGE);
    if (currentPage < totalPages) {
      setCurrentPage((prevPage) => prevPage + 1);
    } else {
      setCurrentPage(1);
    }
  };
console.log(assignments);
const handleUpdateAssignment = (assignmentId) => {
  // Find the assignment in the 'assignments' array by its id
  let assignmentToUpdate = null;
  assignments.forEach((assignment) => {
    const foundAssignment = assignment.assignments.find((a) => a.id === assignmentId);
    if (foundAssignment) {
      assignmentToUpdate = foundAssignment;
      return; // Exit the loop once the assignment is found
    }
  });

  if (assignmentToUpdate) {
    
    setSelectedAssignment(assignmentToUpdate);
    setIsCreateModalOpen(true);
  } else {
    console.log(`Assignment with id ${assignmentId} not found`);
  }
};
  const handleSeeAnswers = (assignmentId) => {
    
    dispatch(getAllSubmissions(assignmentId));
    setIsSeeSubmissionModalOpen(true);
    
  };
  const handleSubmitAssignment = (assignmentId) => {
    console.log(assignmentId);
    setNeededId(assignmentId);
    setIsSubmitModalOpen(true);
  };

  return (
    <>
      <div>
        <table style={{ marginLeft: '70px', width: '900px', marginTop: '80px' }}>
          <thead>
            <tr>
              <th>Schedule Name</th>
              <th>Assignment List</th>
              <th>End Time</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {currentSchedules.map((schedule) => (
              <tr key={schedule.scheduleId}>
                <td>{schedule.scheduleName}</td>
                <td>
                  <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                    {Array.isArray(schedule.assignments)&& schedule.assignments.map((assignment) => (
                      <div key={assignment.id} style={{
                        marginRight: '100px',
                        marginBottom: '31px',
                        borderBottom: '1px solid black',
                        paddingBottom: '5px',
                      }}>
                        <div>{assignment.id}.{assignment.assignmentTitle}</div>
                        {assignment.fileName && (
                          <a
                            href={`http://localhost:8081/api/assignment/files/${encodeURIComponent(assignment.fileName)}`}
                            target="_blank"
                            rel="noopener noreferrer"
                            download={assignment.fileName} // This attribute triggers the download
                          >
                            {assignment.fileName}
                          </a>
                        )}

                      </div>
                    ))}
                  </div>
                </td>
                <td>
                  <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                    {Array.isArray(schedule.assignments) && schedule.assignments.map((assignment) => (
                      <div key={assignment.id} style={{
                        marginRight: '30px', marginBottom: '33px', borderBottom: '1px solid black',
                        paddingBottom: '5px',
                      }}>
                        {assignment.id}. [{assignment.endTime}]
                      </div>
                    ))}
                  </div>
                </td>
                <td>
                  <div className="actions-container" >
                    {Array.isArray(schedule.assignments) && schedule.assignments.map((assignment) => (
                      <div key={assignment.id} className="assignment-actions">
                        <div style={{
                          display: 'flex', alignItems: 'center', marginBottom: '22px', borderBottom: '1px solid black',
                          paddingBottom: '5px',
                        }}>
                          {(role==="TRAINER"|| role==="ADMIN")&& (<button
                            className="small-button"
                            style={{ backgroundColor: 'blue', color: 'white', fontSize: '13px', marginRight: '5px' }}
                            onClick={() => handleUpdateAssignment(assignment.id)}
                          >
                            Update
                          </button>)}
                          {(role==="TRAINER"||role==="ADMIN") && (<button
                            className="small-button"
                            style={{ backgroundColor: 'green', color: 'white', fontSize: '13px', marginRight: '5px' }}
                            onClick={() => handleSeeAnswers(assignment.id)}
                          >
                            See Submissions
                          </button>)}
                          {role === "TRAINEE" && (<button
                            className="small-button"
                            style={{ backgroundColor: 'orange', color: 'white', fontSize: '13px', marginRight: '5px' }}
                            onClick={() => handleSubmitAssignment(assignment.id)}
                          >
                            Submit Answer
                          </button>)}

                        </div>
                      </div>
                    ))}

                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {isCreateModalOpen && (
        <AssignmentModal
          assignment={selectedAssignment}
          closeModal={() => setIsCreateModalOpen(false)}
          isModalOpen = {isCreateModalOpen}
        />
      )}
      {isSeeSubmissionModalOpen && (
        <SeeSubmissionsModal
        assignmentSubmissions={assignmentSubmissions}
          closeModal={() => setIsSeeSubmissionModalOpen(false)}
          isModalOpen = {isSeeSubmissionModalOpen}
        />
      )}
      {isSubmitModalOpen && (
        <SubmitAnswerModal
          assignmentId={neededId}
          closeModal={() => setIsSubmitModalOpen(false)}
          isModalOpen = {isSubmitModalOpen}
        />
      )}
        <div className="pagination" style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
          <button onClick={handlePreviousPage}>Previous</button>
          {Array.from({ length: totalPages }, (_, index) => {
            const page = index + 1;
            if (page === currentPage || page === currentPage - 1 || page === currentPage + 1) {
              return (
                <button
                  key={index}
                  className={currentPage === page ? 'active' : ''}
                  onClick={() => handlePaginationChange(page)}
                >
                  {page}
                </button>
              );
            } else if (
              page === 1 || page === totalPages ||
              (page === currentPage - 2 && currentPage >= 4) ||
              (page === currentPage + 2 && currentPage <= totalPages - 3)
            ) {
              return (
                <span key={index} className="ellipsis">
                  ...
                </span>
              );
            }
            return null;
          })}
          <button onClick={handleNextPage}>Next</button>
        </div>

      </div>
    </>
  );
};

export default AssignmentTable;
