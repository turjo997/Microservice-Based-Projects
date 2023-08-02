import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import * as XLSX from 'xlsx';

import './Approve.scss';

const Approve = () => {
  const [applicants, setApplicants] = useState([]);
  const [admitCards, setAdmitCards] = useState([]);
  const [writtenData, setWrittenData] = useState([]);
  const [sortConfig, setSortConfig] = useState({ key: '', direction: '' });

  useEffect(() => {
    fetchData();
    fetchAdmitCards();
  }, []);

  const fetchData = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('http://localhost:8080/approval/', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setApplicants(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const fetchAdmitCards = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('http://localhost:8080/admitcard/', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setAdmitCards(response.data);
    } catch (error) {
      console.error('Error fetching admit cards:', error);
    }
  };

  const handleApprove = async (applicant) => {
    try {
      const data = {
        applicant: { applicantId: applicant.applicant.applicantId },
        circular: { circularId: applicant.circular.circularId },
        approved: true,
      };
      const token = localStorage.getItem('token');
      await axios.put(`http://localhost:8080/approval/${applicant.approvalId}`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      fetchData();
    } catch (error) {
      console.error('Error approving application:', error);
    }
  };

  const handleGenerateAdmitCard = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:8080/admitcard/', {}, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchAdmitCards();
    } catch (error) {
      console.error('Error generating admit card:', error);
      console.error('Error generating admit card:', error);
      console.log('Response data:', error.response.data);
    }
  };

  const handleGenerateHiddenCode = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:8080/written/auto-create', {}, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchWrittenData();
    } catch (error) {
      console.error('Error generating hidden code:', error);
    }
  };

  const fetchWrittenData = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('http://localhost:8080/written/', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setWrittenData(response.data);
    } catch (error) {
      console.error('Error fetching written data:', error);
    }
  };

  const sortTable = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });
  };

  const getSortedData = () => {
    const { key, direction } = sortConfig;
    if (key === '') return applicants;
    const sortedData = [...applicants].sort((a, b) => {
      if (key === 'applicant.cgpa' || key === 'applicant.passingYear') {
        // Compare as numbers for cgpa and passingYear
        const aValue = parseFloat(a[key]);
        const bValue = parseFloat(b[key]);
        if (aValue < bValue) return direction === 'asc' ? -1 : 1;
        if (aValue > bValue) return direction === 'asc' ? 1 : -1;
        return 0;
      } else {
        // Compare as strings for other fields
        if (a[key] < b[key]) return direction === 'asc' ? -1 : 1;
        if (a[key] > b[key]) return direction === 'asc' ? 1 : -1;
        return 0;
      }
    });
    return sortedData;
  };


  const handleDownloadExcel = (dataToExport) => {
    const worksheet = XLSX.utils.json_to_sheet(dataToExport);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Hidden_Code_Data');

    const excelData = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    const excelBlob = new Blob([excelData], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

    const excelURL = URL.createObjectURL(excelBlob);

    const a = document.createElement('a');
    a.href = excelURL;
    a.download = 'hidden_code_data.xlsx';
    a.style.display = 'none';
    document.body.appendChild(a);
    a.click();

    URL.revokeObjectURL(excelURL);
  };


  const handleDownloadExcelForHiddenCode = () => {
    const dataToExport = writtenData.map(({ hiddenCode, applicantId, circular }) => ({ hiddenCode, applicantId, circular }));
    handleDownloadExcel(dataToExport);
  };

  const handleDownloadExcelForMarkUpload = () => {
    const dataToExport = writtenData.map(({ hiddenCode, mark, circular }) => ({ hiddenCode, mark, circular }));
    handleDownloadExcel(dataToExport);
  };

  return (
    <div className="approve-container">
      <h2>Applicants:</h2>
      <table>
        <thead>
          <tr>
            <th onClick={() => sortTable('applicant.firstName')}>
              Name
              {sortConfig.key === 'applicant.firstName' &&
                (sortConfig.direction === 'asc' ? (
                  <FontAwesomeIcon icon={faSortUp} />
                ) : (
                  <FontAwesomeIcon icon={faSortDown} />
                ))}
            </th>
            <th onClick={() => sortTable('applicant.degreeName')}>
              Degree
              {sortConfig.key === 'applicant.degreeName' &&
                (sortConfig.direction === 'asc' ? (
                  <FontAwesomeIcon icon={faSortUp} />
                ) : (
                  <FontAwesomeIcon icon={faSortDown} />
                ))}
            </th>
            <th>Passing Year</th>
            <th onClick={() => sortTable('applicant.cgpa')}>
              CGPA
              {sortConfig.key === 'applicant.cgpa' &&
                (sortConfig.direction === 'asc' ? (
                  <FontAwesomeIcon icon={faSortUp} />
                ) : (
                  <FontAwesomeIcon icon={faSortDown} />
                ))}
            </th>
            <th onClick={() => sortTable('circular.title')}>
              Circular
              {sortConfig.key === 'circular.title' &&
                (sortConfig.direction === 'asc' ? (
                  <FontAwesomeIcon icon={faSortUp} />
                ) : (
                  <FontAwesomeIcon icon={faSortDown} />
                ))}
            </th>
            <th>Approved</th>
          </tr>
        </thead>
        <tbody>
          {getSortedData().map((applicant) => (
            <tr key={applicant.approvalId}>
              <td>
                {applicant.applicant.firstName} {applicant.applicant.lastName}
              </td>
              <td>{applicant.applicant.degreeName}</td>
              <td>{applicant.applicant.passingYear}</td>
              <td>{applicant.applicant.cgpa}</td>
              <td>{applicant.circular.title}</td>
              <td>
                {applicant.approved ? (
                  <span className="approved">Approved</span>
                ) : (
                  <button onClick={() => handleApprove(applicant)}>Approve</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <button onClick={handleGenerateAdmitCard}>Generate Admit Card</button>

      {admitCards.length > 0 && (
        <>
          <h2>Admit Cards:</h2>
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Serial Number</th>
              </tr>
            </thead>
            <tbody>
              {admitCards.map((admitCard) => (
                <tr key={admitCard.admitcardId}>
                  <td>
                    {admitCard.candidateId.applicant.firstName} {admitCard.candidateId.applicant.lastName}
                  </td>
                  <td>{admitCard.serialNumber}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <button onClick={handleGenerateHiddenCode}>Generate Hidden Code</button>
        </>
      )}

      {writtenData.length > 0 && (
        <>
          <h2>Written Data:</h2>
          <table>
            <thead>
              <tr>
                <th>Applicant ID</th>
                <th>Hidden Code</th>                
                <th>Circular</th>
                {/* <th>Mark</th> */}
              </tr>
            </thead>
            <tbody>
              {writtenData.map((data) => (
                <tr key={data.writtenId}>
                  <td>{data.applicantId}</td>
                  <td>{data.hiddenCode}</td>                                    
                  <td>{data.circular}</td>
                  {/* <td>{data.mark}</td> */}
                </tr>
              ))}
            </tbody>
          </table>
          <button onClick={handleDownloadExcelForHiddenCode}>Download Hidden Code Details</button>
          <button onClick={handleDownloadExcelForMarkUpload}>Download Hidden Code File For Mark Upload</button>

        </>
      )}
    </div>
  );
};

export default Approve;
