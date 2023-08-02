import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import axios from 'axios';

Modal.setAppElement('#root');

const getToken = () => {
  const token = localStorage.getItem('token');
  return token;
};

const PopupWithTabs = ({ isOpen, onClose, activeTab, applicantId, firstName, lastName, circular }) => {
  const [currentTab, setCurrentTab] = useState(activeTab);
  const [marksData, setMarksData] = useState(null);
  const [writtenMarks, setWrittenMarks] = useState(0);
  const [technicalMarks, setTechnicalMarks] = useState(0);
  const [hrMarks, setHrMarks] = useState(0);

  useEffect(() => {
    axios
      .get(`http://localhost:8084/admin/marks/${applicantId}`, {
        headers: {
          Authorization: `Bearer ${getToken()}`, 
        }
      })
      .then((response) => {
        setMarksData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching marks data:', error);
      });
  }, [applicantId, isOpen]); 

  const handleTabSelect = (index) => {
    setCurrentTab(index);
  };

  const handleUpload = () => {
  
    let data = {};
    switch (currentTab) {
      case 0:
        data = { writtenMarks: writtenMarks };
        break;
      case 1:
        data = { technicalMarks: technicalMarks };
        break;
      case 2:
        data = { hrMarks: hrMarks };
        break;
      default:
        break;
    }

    axios
      .put(`http://localhost:8084/admin/marks/update/${applicantId}`, data,{
        headers: {
          Authorization: `Bearer ${getToken()}`, 
          'Content-Type': 'application/json',
        }
      })
      .then((response) => {
        console.log('Marks updated successfully!', response.data);
        axios
          .get(`http://localhost:8084/admin/marks/${applicantId}`,{
            headers: {
              Authorization: `Bearer ${getToken()}`, 
            }
          })
          .then((response) => {
            setMarksData(response.data);
          })
          .catch((error) => {
            console.error('Error fetching marks data:', error);
          });
      })
      .catch((error) => {
        console.error('Error updating marks:', error);

      });
  };

  return (
    <Modal isOpen={isOpen} onRequestClose={onClose}>
      <div>
        <h3>Applicant ID: {applicantId}</h3>
        <h3>Name: {firstName} {lastName}</h3>
        <h3>Circular: {circular}</h3>
      </div>
      <div className="popup-container">
        <div className="tab-container">
          <Tabs selectedIndex={currentTab} onSelect={handleTabSelect}>
            <TabList>
              <Tab>Written Mark</Tab>
              <Tab>Technical Viva</Tab>
              <Tab>HR Viva</Tab>
            </TabList>
            <TabPanel>
              <h2>Upload Written Mark</h2>
              <input
                type="number"
                placeholder="Written Marks"
                value={writtenMarks}
                onChange={(e) => setWrittenMarks(e.target.value)}
              />
              <button onClick={handleUpload}>Upload</button>
            </TabPanel>
            <TabPanel>
              <h2>Upload Technical Viva Mark</h2>
              <input
                type="number"
                placeholder="Technical Viva Marks"
                value={technicalMarks}
                onChange={(e) => setTechnicalMarks(e.target.value)}
              />
              <button onClick={handleUpload}>Upload</button>
            </TabPanel>
            <TabPanel>
              <h2>Upload HR Viva Mark</h2>
              <input
                type="number"
                placeholder="HR Viva Marks"
                value={hrMarks}
                onChange={(e) => setHrMarks(e.target.value)}
              />
              <button onClick={handleUpload}>Upload</button>
            </TabPanel>
          </Tabs>
        </div>
        <div className="applicant-info">
          {marksData ? (
            <table>
              <thead>
                <tr>
                  <th>Paper Code</th>
                  <th>Written Marks</th>
                  <th>Technical Marks</th>
                  <th>Total Score</th>
                  <th>HR Marks</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{marksData.paperCode}</td>
                  <td>{marksData.writtenMarks}</td>
                  <td>{marksData.technicalMarks}</td>
                  <td>{marksData.totalScore}</td>
                  <td>{marksData.hrMarks}</td>
                  <td>{marksData.status}</td>
                </tr>
              </tbody>
            </table>
          ) : (
            <p>Loading marks data...</p>
          )}
        </div>
      </div>
    </Modal>
  );
};

export default PopupWithTabs;
