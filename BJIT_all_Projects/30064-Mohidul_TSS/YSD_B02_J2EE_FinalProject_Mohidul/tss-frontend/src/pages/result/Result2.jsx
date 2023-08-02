import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Result.scss';
import ResourceShow from '../resource/ResourceShow';

const Result = () => {
  const [results, setResults] = useState([]);
  const [sortDirection, setSortDirection] = useState('asc');
  const [isButtonClicked, setIsButtonClicked] = useState(false);
  const [circularTitle, setCircularTitle] = useState('');
  const [applicantID, setApplicantID] = useState('');
  const [circularResults, setCircularResults] = useState([]);
  const [sortedCircularResults, setSortedCircularResults] = useState([]);
  const [applicantResults, setApplicantResults] = useState([]);

  useEffect(() => {
    // Fetch data from the API endpoint
    axios.get('http://localhost:8080/result/all')
      .then((response) => {
        setResults(response.data);
      })
      .catch((error) => {
        console.error('Error fetching results:', error);
      });
  }, []);

  const handleShowScore = () => {
    if (!isButtonClicked) {
      // Send a blank POST request to generate scores
      axios.post('http://localhost:8080/result/generate', {})
        .then(() => {
          // Set the isButtonClicked state to true to hide the button
          setIsButtonClicked(true);
          window.alert(
            "Refresh the page please to show the results."
          );
        })
        .catch((error) => {
          console.error('Error generating scores:', error);
        });
    }
  };

  const handleSortByScore = () => {
    // Toggle sorting direction between 'asc' and 'desc'
    const newSortDirection = sortDirection === 'asc' ? 'desc' : 'asc';
    setSortDirection(newSortDirection);

    // Sort the results array by the score in the selected direction
    const sortedResults = [...results].sort((a, b) => {
      if (newSortDirection === 'asc') {
        return a.score - b.score;
      } else {
        return b.score - a.score;
      }
    });

    setResults(sortedResults);
  };

  const handleSearchByCircular = () => {
    if (circularTitle.trim() !== '') {
      axios.get(`http://localhost:8080/result/circular/${circularTitle}`)
        .then((response) => {
          setCircularResults(response.data);
          setSortedCircularResults(response.data); // Initialize sortedCircularResults
        })
        .catch((error) => {
          console.error('Error fetching results by circular:', error);
        });
    }
  };



  const handleSortByScoreCircular = () => {
    // Toggle sorting direction between 'asc' and 'desc'
    const newSortDirection = sortDirection === 'asc' ? 'desc' : 'asc';
    setSortDirection(newSortDirection);

    // Sort the circularResults array by the score in the selected direction
    const sortedResults = [...circularResults].sort((a, b) => {
      if (newSortDirection === 'asc') {
        return a.score - b.score;
      } else {
        return b.score - a.score;
      }
    });

    setSortedCircularResults(sortedResults);
  };

  const getSortIcon = () => {
    return sortDirection === 'asc' ? '▲' : '▼';
  };


  return (
    <div className="result">
      {!isButtonClicked && (
        <button onClick={handleShowScore}>Generate Score</button>
      )}
      <table>
        <thead>
          <tr>
            <th>Applicant ID</th>
            <th onClick={handleSortByScore}>
              Circular Title {getSortIcon()}{' '}
            </th>
            <th onClick={handleSortByScore}>
              Score {getSortIcon()}
            </th>
          </tr>
        </thead>
        <tbody>
          {results.map((result) => (
            <tr key={result.resultId}>
              <td>{result.applicantId}</td>
              <td>{result.circularTitle}</td>
              <td>{result.score}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div>
        <h2>Results by Circular Title</h2>
        <div>
          <input
            type="text"
            placeholder="Enter Circular Title"
            value={circularTitle}
            onChange={(e) => setCircularTitle(e.target.value)}
          />
          <button onClick={handleSearchByCircular}>Search</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>Applicant ID</th>
              <th >Circular Title</th>
              <th onClick={handleSortByScoreCircular}> Score {getSortIcon()} </th>
            </tr>
          </thead>
          <tbody>
            {sortedCircularResults.map((result) => (
              <tr key={result.resultId}>
                <td>{result.applicantId}</td>
                <td>{result.circularTitle}</td>
                <td>{result.score}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>



    </div>
  );
};

export default Result;