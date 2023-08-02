import React, { useContext } from 'react'
import { Box, Card, CardContent, CardMedia, Typography, Button, Grid } from '@mui/material';
import { LoginContext } from '../context/LoginContex';
import { styled } from '@mui/material/styles'; 
import ClipMessage from "../services/ClipMessage"



const CourseCard = styled(Card)(({ theme, role, loggedIn }) => ({
    display: 'flex',
    flexDirection: 'column',
    height: '100%',
    borderRadius: theme.spacing(1),
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    transition: 'transform 0.2s ease-in-out',
    cursor: (role === 'ADMIN' || loggedIn) ? 'pointer' : 'default',
    '&:hover': {
        transform: (role === 'ADMIN' || loggedIn) ? 'scale(1.03)' : 'none',
    },
}));

const CourseMedia = styled(CardMedia)(({ theme }) => ({
    height: 100,
    objectFit: 'cover',
    borderTopLeftRadius: theme.spacing(1),
    borderTopRightRadius: theme.spacing(1),
}));

const CourseContent = styled(CardContent)(({ theme }) => ({
    flexGrow: 1,
    padding: theme.spacing(2),
}));

const CourseTitle = styled(Typography)(({ theme }) => ({
    fontWeight: 600,
    fontSize: '1.2rem',
}));

const CourseDescription = styled(Typography)(({ theme }) => ({
    marginTop: theme.spacing(1),
    color: theme.palette.text.secondary,
}));

const EnrollButton = styled(Button)(({ theme }) => ({
    marginTop: 'auto',
    borderRadius: theme.spacing(0),
    fontWeight: 600,
}));

const CourseCards = ({ courses, setValue, setSingleCourse, pathValue, courseButtonText }) => {
    const { role } = useContext(LoginContext);
    return (
        <Box >

            <Grid container spacing={2} justifyContent="center">
                {/* {courses?.data.data.listResponse.map((course) => ( */}

                {courses?.map((course) => (
                    <Grid item key={course.courseId} xs={12} sm={6} md={4}>
                        <Box onClick={() => {
                            setValue(pathValue);
                            setSingleCourse(course)
                        }}>

                            <CourseCard sx={{height:"240px"}} >
                                <CourseMedia component="img" image={course.imageUrl} alt={course.title} />
                                <CourseContent>
                                    <CourseTitle variant="h6">{course.courseName}</CourseTitle>
                                    <CourseDescription variant="body2">{ClipMessage(course.courseDescription,45)} </CourseDescription>
                                </CourseContent>
                                <EnrollButton sx={{bgcolor:"#2d2c72"}} variant="contained" color="primary" size="small">
                                    {courseButtonText}
                                </EnrollButton>
                            </CourseCard>
                        </Box>

                    </Grid>
                ))}
            </Grid>

        </Box>)
}

export default CourseCards