import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Grid, Typography, Paper } from '@mui/material';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, PieChart, Pie, Cell, ResponsiveContainer } from 'recharts';

const AdminDashboard = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios
      .get('http://localhost:8082/applications/all', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      })
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);

  // Function to get the count of applicants for each circular
  const getApplicantCountByCircular = () => {
    const circularData = {};

    data.forEach((application) => {
      const circularName = application.jobCircular.circularName;

      if (!circularData[circularName]) {
        circularData[circularName] = 1;
      } else {
        circularData[circularName]++;
      }
    });

    return Object.keys(circularData).map((name) => ({ name, value: circularData[name] }));
  };

  // Function to get the count of applicants for each status
  const getApplicantCountByStatus = () => {
    const statusData = {};

    data.forEach((application) => {
      const status = application.status.status;

      if (!statusData[status]) {
        statusData[status] = 1;
      } else {
        statusData[status]++;
      }
    });

    return Object.keys(statusData).map((status) => ({ name: status, value: statusData[status] }));
  };

  // Function to get the count of applicants for each educational institute
  const getApplicantCountByEducationalInstitute = () => {
    const instituteData = {};

    data.forEach((application) => {
      const educationalInstitute = application.applicants.educationalInstitute;

      if (!instituteData[educationalInstitute]) {
        instituteData[educationalInstitute] = 1;
      } else {
        instituteData[educationalInstitute]++;
      }
    });

    return Object.keys(instituteData).map((institute) => ({ name: institute, value: instituteData[institute] }));
  };
  

  const colors = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

  return (
    <div>
      <Typography variant="h4" align="center" gutterBottom>
        Admin Dashboard
      </Typography>

      <Grid container spacing={2}>
        {/* Circular Wise Applicant Count */}
        <Grid item xs={12} md={12}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" align="center" gutterBottom>
              Circular Wise Applicant Count
            </Typography>
            <div style={{ height: '300px', overflowX: 'auto' }}>
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={getApplicantCountByCircular()}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="value" fill="#8884d8" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </Paper>
        </Grid>

        {/* Status Wise Applicant Count */}
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" align="center" gutterBottom>
              Status Wise Applicant Count
            </Typography>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie data={getApplicantCountByStatus()} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={100} fill="#8884d8" label>
                  {getApplicantCountByStatus().map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={colors[index % colors.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </Paper>
        </Grid>

        {/* Educational Institute Wise Applicant Count */}
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" align="center" gutterBottom>
              Educational Institute Wise Applicant Count
            </Typography>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie data={getApplicantCountByEducationalInstitute()} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={100} fill="#8884d8" label>
                  {getApplicantCountByEducationalInstitute().map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={colors[index % colors.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </Paper>
        </Grid>
      </Grid>
    </div>
  );
};

export default AdminDashboard;
