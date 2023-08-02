import React, { useEffect, useState } from 'react';
import { Table, Dropdown, DropdownToggle, DropdownMenu, DropdownItem, Button } from 'reactstrap';
import axios from 'axios';
import PopupWithTabs from './PopupWithTabs'; 

function SelectedCandidate() {
  const [applicants, setApplicants] = useState([]);
  const [filteredApplicants, setFilteredApplicants] = useState([]);
  const [selectedCircular, setSelectedCircular] = useState('');
  const [uniqueCircularNames, setUniqueCircularNames] = useState(new Set());
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [isPopupOpen, setIsPopupOpen] = useState(false); 
  const [activeTab, setActiveTab] = useState(0); 
  const [selectedApplicant, setSelectedApplicant] = useState(null); 

  useEffect(() => {
    
    const fetchData = async () => {
      try {
        
        const token = localStorage.getItem('token');

        const response = await axios.get('http://localhost:8084/admin/approvedApplications', {
          headers: {
            Authorization: `Bearer ${token}` 
          }
        });

        setApplicants(response.data);
        setFilteredApplicants(response.data);

        const circularNamesSet = new Set(response.data.map(applicant => applicant.circular.circularName));
        setUniqueCircularNames(circularNamesSet);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    if (selectedCircular === '') {
      setFilteredApplicants(applicants);
    } else {
      const filteredData = applicants.filter(applicant => applicant.circular.circularName === selectedCircular);
      setFilteredApplicants(filteredData);
    }
  }, [selectedCircular, applicants]);

  const toggleDropdown = () => {
    setDropdownOpen(prevState => !prevState);
  };

  const handleDropdownSelect = (circularName) => {
    setSelectedCircular(circularName);
  };

  const handleUploadMark = (applicantId) => {

    const selectedApplicant = applicants.find(
      (applicant) => applicant.applicantEntity.applicantId === applicantId
    );

    setIsPopupOpen(true);
    setActiveTab(0); 
    setSelectedApplicant(selectedApplicant);
  };

  const handleClosePopup = () => {
    setIsPopupOpen(false); 
    setSelectedApplicant(null); 
  };

  return (
    <div>
      <h1>Selected Candidates</h1>
      <Dropdown isOpen={dropdownOpen} toggle={toggleDropdown}>
        <DropdownToggle caret>
          {selectedCircular || 'All'}
        </DropdownToggle>
        <DropdownMenu>
          <DropdownItem onClick={() => handleDropdownSelect('')}>All</DropdownItem>
          {Array.from(uniqueCircularNames).map((circularName) => (
            <DropdownItem key={circularName} onClick={() => handleDropdownSelect(circularName)}>
              {circularName}
            </DropdownItem>
          ))}
        </DropdownMenu>
      </Dropdown>
      <Table striped bordered responsive>
        <thead>
          <tr>
            <th>Applicant ID</th>
            <th>Name</th>
            <th>Circular Name</th>
            <th>Approval Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredApplicants.map((applicant) => (
            <tr key={applicant.applicationId}>
              <td>{applicant.applicantEntity.applicantId}</td>
              <td>{applicant.applicantEntity.firstName} {applicant.applicantEntity.lastName}</td>
              <td>{applicant.circular.circularName}</td>
              <td>{applicant.approvalStatus ? 'Approved' : 'Not Approved'}</td>
              <td>
                <Button color="primary" onClick={() => handleUploadMark(applicant.applicantEntity.applicantId)}>
                  Upload Mark
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Render the pop-up window with tabs */}
      {isPopupOpen && selectedApplicant && (
        <PopupWithTabs
          isOpen={isPopupOpen}
          onClose={handleClosePopup}
          activeTab={activeTab}
          applicantId={selectedApplicant.applicantEntity.applicantId}
          firstName={selectedApplicant.applicantEntity.firstName}
          lastName={selectedApplicant.applicantEntity.lastName}
          circular={selectedApplicant.circular.circularName}
        />
      )}
    </div>
  );
}

export default SelectedCandidate;
