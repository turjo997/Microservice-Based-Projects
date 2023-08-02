import React, { useEffect, useState } from 'react';
import './ResourceShow.scss'; 

const ResourceShow = ({ applicantId }) => {
  const [resourceData, setResourceData] = useState(null);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8080/resource/applicant/${applicantId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setResourceData(data);
      })
      .catch((error) => {
        console.error('Error fetching resource data:', error);
      });
  }, [applicantId]);

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <div className="resource-show">
      {resourceData ? (
        <>
          <button onClick={handleShowModal}>View Details</button>
          {showModal && (
            <div className="modal-overlay" onClick={handleCloseModal}>
              <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="card">
                  <div className="card-header">
                    <h3>Applicant Information</h3>
                    <button className="close-button" onClick={handleCloseModal}>Close</button>
                  </div>
                  <div className="card-body">
                    <p>
                      <strong>Applicant ID:</strong> {resourceData.applicant.applicantId}
                    </p>
                    <p>
                      <strong>Name:</strong> {`${resourceData.applicant.firstName} ${resourceData.applicant.lastName}`}
                    </p>
                    <p>
                      <strong>Email:</strong> {resourceData.applicant.email}
                    </p>
                    <div className="photo">
                      {resourceData.photoData && (
                        <img src={`data:image/jpeg;base64,${resourceData.photoData}`} alt="Applicant Photo" />
                      )}
                    </div>
                    {resourceData.cvData && (
                      <div className="cv">
                        <a href={`data:application/pdf;base64,${resourceData.cvData}`} download="cv.pdf">
                          Download CV
                        </a>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
        </>
      ) : (
        <div>Loading...</div>
      )}
    </div>
  );
};

export default ResourceShow;
