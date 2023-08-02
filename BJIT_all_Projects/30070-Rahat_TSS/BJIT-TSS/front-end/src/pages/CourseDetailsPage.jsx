import React, { useContext, useEffect, useState } from 'react';
import { Box, Typography, Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useNavigate, useParams } from 'react-router-dom/dist/index';
import useFetch from '../hooks/useFetch';
import axios from '../api/axios';
import { Alert } from 'react-bootstrap';
import { LoginContext } from '../context/LoginContex';
import JSON2Message from '../services/JSON2Message';


const CourseTitle = styled(Typography)(({ theme }) => ({
    fontWeight: 600,
    fontSize: '1.8rem',
    marginBottom: theme.spacing(2),
}));

const CourseDetails = styled(Typography)(({ theme }) => ({
    marginBottom: theme.spacing(2),
}));

const ApplyButton = styled(Button)(({ theme }) => ({
    marginTop: theme.spacing(1),
    borderRadius: theme.spacing(0),
    fontWeight: 600,
}));

const CourseDescriptionComponent = () => {
    const {loggedIn} = useContext(LoginContext)
    const { data, loading, error } = useFetch("api/course");
    const [courses, setCourses] = useState(null);

    const navigate = useNavigate();


    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("")

    const [batchCode, setBatchCode] = useState("")

    const [course, setCourse] = useState(null)

    useEffect(() => {
        if (course != null) {
            setBatchCode(course.batchCode)

        }
    }, [course])
    useEffect(() => {
        setCourses(data?.data?.data?.listResponse);
    }, [data]);

    const { id } = useParams();
    const courseIdNumber = parseInt(id, 10);

    useEffect(() => {
        if (courses) {
            setCourse(courses.find((c) => c.courseId === courseIdNumber));
        }
    }, [courses, courseIdNumber]);

    // Rest of the code...

    if (!courses) {
        return null; // Return null while waiting for course data to be fetched
    }

    if (!course) {
        return <Typography variant="h6">Course not found</Typography>;
    }





    const applyForCourse = async () => {

        if(!loggedIn){
            navigate("/login")
        }

        const data = {
            batchCode: batchCode,
        };
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };


        await axios.post('/api/application/apply', data, config)
            .then((response) => {
               

                if (response.status === 201) {


                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)

                    setTimeout(() => {
                        setShowSuccessMessage(false)
                        setSuccessMessage("")
                        navigate("/")

                        


                    }, 2000);

                }



            })
            .catch((error) => {
                console.error('Error :', error.response.data.errorMessage);
                setShowErrorMessage(true)
                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

                setTimeout(() => {
                    setShowErrorMessage(false)
                    setErrorMessage("")

                }, 2000);


            });
    };

    return (
        <Box mt={4}>
            
            <CourseTitle variant="h1">{course.courseName || 'N/A'}</CourseTitle>
            <CourseDetails variant="body1">
                <strong>Batch Code:</strong> {course.batchCode || 'N/A'}
            </CourseDetails>
            <CourseDetails variant="body1">
                <strong>Start Date:</strong> {course.startDate ? new Date(course.startDate).toLocaleDateString() : 'N/A'}
            </CourseDetails>
            <CourseDetails variant="body1">
                <strong>End Date:</strong> {course.endDate ? new Date(course.endDate).toLocaleDateString() : 'N/A'}
            </CourseDetails>
            <CourseDetails variant="body1">
                <strong>Application Deadline:</strong> {course.applicationDeadline ? new Date(course.applicationDeadline).toLocaleDateString() : 'N/A'}
            </CourseDetails>
            <CourseDetails variant="body1">
                <strong>Vacancy:</strong> {course.vacancy || 'N/A'}
            </CourseDetails>
            <CourseDetails variant="body1">
                <strong>Written Exam Time:</strong> {course.writtenExamTime ? new Date(course.writtenExamTime).toLocaleDateString() : 'N/A'}
            </CourseDetails>
            <CourseDetails variant="body1">
                <strong>Course Description:</strong> {course.courseDescription || 'N/A'}
            </CourseDetails>
            <ApplyButton variant="contained" color="primary" size="large" onClick={applyForCourse}>
                Apply Now
            </ApplyButton>

            {showSuccessMessage && (
        <Alert variant="success">{successMessage}</Alert>
      )}
      {showErrorMessage && (
        <Alert variant="danger">{errorMessage}</Alert>
      )}

        </Box>
    );
};

export default CourseDescriptionComponent;
