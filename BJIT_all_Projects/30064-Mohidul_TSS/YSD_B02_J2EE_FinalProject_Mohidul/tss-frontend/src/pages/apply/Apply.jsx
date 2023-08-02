import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Apply.scss';

const Apply = () => {
    const [circularData, setCircularData] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [applicantId, setApplicantId] = useState('');
    const [selectedCircularId, setSelectedCircularId] = useState('');
    const [errorMessages, setErrorMessages] = useState({
        applicantId: '',
    });
    const [appliedCirculars, setAppliedCirculars] = useState([]);

    
    const fetchCircularData = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/circular/', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setCircularData(response.data);
        } catch (error) {
            console.error('Error fetching circular data:', error);
        }
    };

    
    useEffect(() => {
        
        const fetchAppliedCirculars = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8080/applied-circulars/', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setAppliedCirculars(response.data);
            } catch (error) {
                console.error('Error fetching applied circulars:', error);
            }
        };

        fetchCircularData();
        fetchAppliedCirculars();
    }, []);

    const handleApplyClick = (circularId) => {
        if (!appliedCirculars.includes(circularId)) {
            setShowForm(true);
            setSelectedCircularId(circularId);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setErrorMessages({ applicantId: '' });

        let hasError = false;

        if (!applicantId.trim()) {
            setErrorMessages((prevState) => ({
                ...prevState,
                applicantId: 'Temporary ID cannot be blank',
            }));
            hasError = true;
        }

        if (hasError) {
            return;
        }

        const data = {
            applicant: {
                applicantId: parseInt(applicantId),
            },
            circular: {
                circularId: selectedCircularId,
            },
            approved: false,
        };

        try {
            const token = localStorage.getItem('token');
            await axios.post('http://localhost:8080/approval/', data, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            console.log('Application submitted successfully:', data);
        } catch (error) {
            console.error('Error submitting application:', error.response?.data?.message || error.message);
            setErrorMessages((prevState) => ({
                ...prevState,
                applicantId: error.response?.data?.message || 'An error occurred while submitting the application.',
            }));
        }

        setShowForm(false);
        setApplicantId('');
        setSelectedCircularId('');
    };

    const handleCancelClick = () => {
        setShowForm(false);
        setApplicantId('');
        setSelectedCircularId('');
        setErrorMessages({ applicantId: '' });
    };

    const handleApplicantIdChange = (encodedApplicantId) => {
        try {
            const decodedApplicantId = atob(encodedApplicantId);
            setApplicantId(decodedApplicantId);
        } catch (error) {
            console.error('Error decoding applicantId:', error);
        }
    };

    return (
        <div className="apply-container">
            <h2>Available Circulars</h2>
            <div className="card-container">
                {circularData.map((circular) => (
                    <div key={circular.circularId} className="circular-card">
                        <h3>{circular.title}</h3>
                        <p>{circular.description}</p>
                        <button
                            onClick={() => handleApplyClick(circular.circularId)}
                            disabled={appliedCirculars.includes(circular.circularId)}
                        >
                            {appliedCirculars.includes(circular.circularId) ? 'Applied' : 'Apply'}
                        </button>
                    </div>
                ))}
            </div>

            {showForm && (
                <div className="apply-form">
                    <span className="close-btn" onClick={handleCancelClick}>
                        &times;
                    </span>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="applicantId">Temporary ID:</label>
                        <input
                            type="text"
                            id="applicantId"                        
                            onChange={(e) => handleApplicantIdChange(e.target.value)}
                        />
                        {errorMessages.applicantId && <p className="error-message">{errorMessages.applicantId}</p>}
                        <button type="submit">Apply</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default Apply;
