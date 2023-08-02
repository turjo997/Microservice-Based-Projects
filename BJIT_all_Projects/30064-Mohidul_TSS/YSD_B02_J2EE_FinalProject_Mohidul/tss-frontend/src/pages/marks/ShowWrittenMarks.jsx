import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ShowWrittenMarks.scss';

const ShowWrittenMarks = () => {
    const [writtenMarks, setWrittenMarks] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function fetchData() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8080/written/', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setWrittenMarks(response.data);
                setIsLoading(false);
            } catch (error) {
                console.error('Error fetching data:', error);
                setIsLoading(false);
            }
        }

        fetchData();
    }, []);

    return (
        <div className="show-written-marks">
            <h2>Written Marks</h2>
            {isLoading ? (
                <div className="loading-spinner"></div>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>Hidden Code</th>
                            <th>Applicant ID</th>
                            <th>Mark</th>
                            {/* <th>Circular</th> */}
                        </tr>
                    </thead>
                    <tbody>
                        {writtenMarks.map((item) => (
                            <tr key={item.writtenId}>
                                <td>{item.hiddenCode}</td>
                                <td>{item.applicantId}</td>
                                <td>{item.mark}</td>
                                {/* <td>{item.circular}</td> */}
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default ShowWrittenMarks;
