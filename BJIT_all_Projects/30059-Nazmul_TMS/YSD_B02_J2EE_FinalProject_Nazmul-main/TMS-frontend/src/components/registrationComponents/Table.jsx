import React, { useState, useEffect } from 'react';
import './Table.css'; // Import custom CSS file for styling
import ReactPaginate from 'react-paginate';

const Table = () => {
  const [showModal, setShowModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [selectedRole, setSelectedRole] = useState('All');
  const [users, setUsers] = useState([]);
  const [currentUsers, setCurrentUsers] = useState([]);
  const [error, setError] = useState(null);
  const [pageCount, setPageCount] = useState(0);

  const token = localStorage.getItem('token');
  const usersPerPage = 5; // Number of users to display per page
  const fetchData =  async() => {
    
    try {
        console.log("data fetching")
      const response =  await fetch('http://localhost:8081/api/user/get-all-users',{
          method:'GET',
          headers: {
            Authorization:`${token}`,
            'Content-Type':'application/json',
          },
        });
      if (response.status==403) {
        console.error("Token expired");
    localStorage.removeItem('token'); // Remove token from local storage
    localStorage.removeItem('email');
    // Redirect to the login page
    window.location.href = '/'; // Replace '/login' with the actual login page URL
    return;
      }
      const data =  await response.json();
      setUsers(data);
      
    } catch (error) {
      setError(error.message);
  } 
  };
  
useEffect(() => {
    fetchData();
  }, []);

  
  const handleShowProfile = (user) => {
    setSelectedUser(user);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };
  useEffect(() => {
    if(users.length>0){
        let filteredData = selectedRole === 'All' ? users : users.filter(user => user.role === selectedRole.toUpperCase());
        let newPageCount = Math.ceil(filteredData.length / usersPerPage);
        setPageCount(newPageCount);
    // setCurrentPage(0);

        let offset = currentPage * usersPerPage;
        let newCurrentUsers = filteredData.slice(offset, offset + usersPerPage);
    
    setCurrentUsers(newCurrentUsers);
    }
  }, [users, selectedRole, currentPage]);
  // Pagination
//   const filteredData = selectedRole === 'All' ? users : users.filter(user => user.role === selectedRole);
//   const pageCount = Math.ceil(users.length / usersPerPage);
//   const offset = currentPage * usersPerPage;
//   const currentUsers = filteredData.slice(offset, offset + usersPerPage);
  const handlePageChange = ({ selected }) => {
    setCurrentPage(selected);
  }
  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
    setCurrentPage(0);
  };

  const renderPagination = () => {
    return (
      <div className="pagination-container">
        <ReactPaginate
          previousLabel={'Prev'}
          nextLabel={'Next'}
          breakLabel={'...'}
          breakClassName={'break-me'}
          pageCount={pageCount}
          marginPagesDisplayed={2}
          pageRangeDisplayed={2}
          onPageChange={handlePageChange}
          containerClassName={'pagination'}
          subContainerClassName={'pages pagination'}
          activeClassName={'active'}
          pageLinkClassName={'page-link'}
          previousLinkClassName={'page-link'}
          nextLinkClassName={'page-link'}
          breakLinkClassName={'page-link'}
          disabledClassName={'disabled'}
        />
      </div>
    );
  };
  const profileDetailStyle = {
    marginBottom: '10px',
  };
  return (
    <div className="table-container">
        <div className="dropdown">
        <select value={selectedRole} onChange={handleRoleChange}>
          <option value="All">All</option>
          <option value="Trainee">Trainee</option>
          <option value="Trainer">Trainer</option>
        </select>
      </div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Email</th>
            <th>FullName</th>
            <th>Phone No</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {currentUsers.map((user) => (
            <tr key={user.id}>
              
              <td>{user.id}</td>
              <td>
                <img src={`http://localhost:8081/api/user/images/${user.image}`} alt={`User ${user.id}`} className="user-image" />
              </td>
              <td>{user.email}</td>
              <td>{user.fullName}</td>
              
              <td>{user.phoneNo}</td>
              <td>{user.role}</td>
              <td>
                <button onClick={() => handleShowProfile(user)}>View Profile</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        <div className="table-modal">
          <div className="modal-content">
            <span className="close" onClick={closeModal}>
              &times;
            </span>
            <h2>User Profile</h2>
            {selectedUser && (
              <div className="profile-details">
                <div className="profile-image-container">
                <img src={`http://localhost:8081/api/user/images/${selectedUser.image}`} alt={`User ${selectedUser.id}`} className="user-image" />
                </div>
                <div className="profile-info">
                <div style={profileDetailStyle}>
                  <strong>ID:</strong> {selectedUser.id}
                </div>
                <div style={profileDetailStyle}>
                  <strong>Email:</strong> {selectedUser.email}
                </div>
                <div style={profileDetailStyle}>
                  <strong>Full Name:</strong> {selectedUser.fullName}
                </div>
                
                <div style={profileDetailStyle}>
                  <strong>Phone No:</strong> {selectedUser.phoneNo}
                </div>
                <div style={profileDetailStyle}>
                  <strong>Role:</strong> {selectedUser.role}
                </div>
                </div>
                
              </div>
            )}
          </div>
        </div>
      )}
      {renderPagination()}
    </div>
  );
};

export default Table;
