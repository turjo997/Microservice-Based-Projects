import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { Grid, Box, Typography, TextField, Button, Container, Paper } from '@mui/material';
import { styled } from '@mui/material/styles';

const StyledContainer = styled(Container)(({ theme }) => ({
  marginTop: theme.spacing(4),
  padding: theme.spacing(3),
  background: '#f0f0f0',
  borderRadius: '8px',
}));

const Register = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    getValues,
  } = useForm();

  const onSubmit = async (data) => {
    try {
      // Log the form data
      console.log('Form Data:', data);
  
      // Create the JSON object to send
      const formDataToSend = {
        firstName: data.firstName,
        lastName: data.lastName,
        gender: data.gender,
        dateOfBirth: data.dateOfBirth,
        email: data.email,
        contactNumber: data.contactNumber,
        degreeName: data.degreeName,
        educationalInstitute: data.educationalInstitute,
        cgpa: data.cgpa,
        passingYear: data.passingYear,
        presentAddress: data.presentAddress,
        password: data.password,
        photo: data.photo, // Send the file location string for photo
        resume: data.resume, // Send the file location string for resume
      };
  
      // Send the POST request to the API
      const response = await axios.post('http://localhost:8082/applicants/register', formDataToSend, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      console.log('Registration successful:', response.data);
      // You can handle successful registration here (e.g., show success message, redirect to login page, etc.)
    } catch (error) {
      console.error('Error registering:', error);
      // Handle the registration error
    }
  };
  

  return (
    <StyledContainer maxWidth="sm">
      <Typography variant="h5" align="center" gutterBottom>
        Register
      </Typography>
      <Paper elevation={3} sx={{ padding: 3 }}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={2}>
            {/* Add more form fields here */}
            <Grid item xs={12}>
              <TextField label="First Name" variant="outlined" fullWidth {...register('firstName', { required: 'First Name is required' })} />
              {errors.firstName && <Typography color="red">{errors.firstName.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <TextField label="Last Name" variant="outlined" fullWidth {...register('lastName', { required: 'Last Name is required' })} />
              {errors.lastName && <Typography color="red">{errors.lastName.message}</Typography>}
            </Grid>
            <Grid item xs={6}>
              <TextField label="Gender" variant="outlined" fullWidth {...register('gender', { required: 'Gender is required' })} />
              {errors.gender && <Typography color="red">{errors.gender.message}</Typography>}
            </Grid>
            <Grid item xs={6}>
              <div>
                <input type="date" {...register('dateOfBirth', { required: 'Date of Birth is required' })} />
              </div>
              {errors.dateOfBirth && <Typography color="red">{errors.dateOfBirth.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <TextField label="Email" variant="outlined" fullWidth {...register('email', { required: 'Email is required', pattern: { value: /^\S+@\S+$/i, message: 'Invalid email address' } })} />
              {errors.email && <Typography color="red">{errors.email.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Contact Number"
                variant="outlined"
                fullWidth
                {...register('contactNumber', { required: 'Contact Number is required' })}
              />
              {errors.contactNumber && <Typography color="red">{errors.contactNumber.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <TextField label="Degree Name" variant="outlined" fullWidth {...register('degreeName', { required: 'Degree Name is required' })} />
              {errors.degreeName && <Typography color="red">{errors.degreeName.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Educational Institute"
                variant="outlined"
                fullWidth
                {...register('educationalInstitute', { required: 'Educational Institute is required' })}
              />
              {errors.educationalInstitute && <Typography color="red">{errors.educationalInstitute.message}</Typography>}
            </Grid>
            <Grid item xs={6}>
              <TextField label="CGPA" variant="outlined" fullWidth {...register('cgpa', { required: 'CGPA is required' })} />
              {errors.cgpa && <Typography color="red">{errors.cgpa.message}</Typography>}
            </Grid>
            <Grid item xs={6}>
              <TextField label="Passing Year" variant="outlined" fullWidth {...register('passingYear', { required: 'Passing Year is required' })} />
              {errors.passingYear && <Typography color="red">{errors.passingYear.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Present Address"
                variant="outlined"
                fullWidth
                {...register('presentAddress', { required: 'Present Address is required' })}
              />
              {errors.presentAddress && <Typography color="red">{errors.presentAddress.message}</Typography>}
            </Grid>
            <Grid item xs={12}>
              <input type="file" accept="image/*" {...register('photo')} />
            </Grid>
            <Grid item xs={12}>
              <input type="file" accept=".pdf" {...register('resume')} />
            </Grid>
            <Grid item xs={6}>
              <TextField
                label="Password"
                variant="outlined"
                fullWidth
                type="password"
                {...register('password', { required: 'Password is required', minLength: { value: 6, message: 'Password must be at least 6 characters long' } })}
              />
              {errors.password && <Typography color="red">{errors.password.message}</Typography>}
            </Grid>
            
            <Grid item xs={12}>
              <Button variant="contained" color="primary" fullWidth type="submit">
                Register
              </Button>
            </Grid>
          </Grid>
        </form>
        <Typography sx={{ mt: 2 }} align="center">
          Already have an account? <Link to="/login">Login here</Link>.
        </Typography>
      </Paper>
    </StyledContainer>
  );
};

export default Register;
