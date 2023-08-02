import React from 'react'
import { TextField, Box, Typography, Button, Grid, Checkbox, FormControlLabel } from '@mui/material';
import { styled } from '@mui/material/styles';
import Alert from 'react-bootstrap/Alert';


const FormContainer = styled(Box)(({ theme }) => ({
    // maxWidth: 90 %,
    margin: '0 auto',
    padding: theme.spacing(4),
    borderRadius: theme.spacing(2),
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
}));

const FormTitle = styled(Typography)(({ theme }) => ({
    fontWeight: 600,
    fontSize: '1.8rem',
    marginBottom: theme.spacing(2),
}));

const FormField = styled(TextField)(({ theme }) => ({
    marginBottom: theme.spacing(2),
}));

const SubmitButton = styled(Button)(({ theme }) => ({
    marginTop: theme.spacing(2),
    borderRadius: theme.spacing(0),
    fontWeight: 600,
}));


const CourseComponent = ({  buttonText, handleSubmit, courseName, setCourseName, batchCode, setBatchCode, isAvailable, setIsAvailable, showSuccessMessage, successMessage, showErrorMessage, errorMessage, courseDescription, setCourseDescription, vacancy, setVacancy, applicationDeadline, setApplicationDeadline, writtenExamTime, setWrittenExamTime, startDate, setStartDate, endDate, setEndDate, applicantDashboardMessage, setApplicantDashboardMessage, writtenShortlistedDashboardMessage, setWrittenShortlistedDashboardMessage, writtenPassedDashboardMessage, setWrittenPassedDashboardMessage, technicalVivaPassedDashboardMessage, setTechnicalVivaPassedDashboardMessage, aptitudeTestPassedDashboardMessage, setAptitudeTestPassedDashboardMessage, hrVivaPassedDashboardMessage, setHrVivaPassedDashboardMessage, traineeDashboardMessage, setTraineeDashboardMessage, writtenExamInstruction , setWrittenExamInstruction }) => {


    return (

        <Box pt={2} pb={2}  >
            <Box sx={{ bgcolor: "white" }}>


                <Grid container spacing={2} justifyContent="center">

                    <FormContainer>
                        <form onSubmit={handleSubmit}>
                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <FormField
                                        label="Course Name"
                                        variant="outlined"
                                        fullWidth
                                        value={courseName}
                                        onChange={(e) => setCourseName(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <FormField
                                        label="Batch Code"
                                        variant="outlined"
                                        fullWidth
                                        value={batchCode}
                                        onChange={(e) => setBatchCode(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Course Description"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={courseDescription}
                                        onChange={(e) => setCourseDescription(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={6} sm={6} >
                                    <Typography variant="h5" color="initial">Vacancy</Typography>
                                </Grid>

                                <Grid item xs={6} sm={6}>
                                    <FormField
                                        label="Vacancy"
                                        variant="outlined"
                                        type="number"
                                        fullWidth
                                        value={vacancy}
                                        onChange={(e) => setVacancy(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={6} sm={6} >
                                    <Typography variant="h5" color="initial">Application Deadline</Typography>
                                </Grid>
                                <Grid item xs={6} sm={6}>
                                    <FormField
                                        type="date"
                                        variant="outlined"
                                        fullWidth
                                        value={applicationDeadline}
                                        onChange={(e) => setApplicationDeadline(e.target.value)}
                                    />
                                </Grid>

                                <Grid item xs={6} sm={6}>
                                    <Typography variant="h5" color="initial">Written Exam Time</Typography>
                                </Grid>
                                <Grid item xs={6} sm={6}>
                                    <FormField
                                        type="datetime-local"
                                        variant="outlined"
                                        fullWidth
                                        value={writtenExamTime}
                                        onChange={(e) => setWrittenExamTime(e.target.value)}
                                    />
                                </Grid>

                                <Grid item xs={6} sm={6}>

                                    <Typography variant="h5" color="initial">Start Date</Typography>
                                </Grid>
                                <Grid item xs={6} sm={6}>

                                    <FormField
                                        type="date"
                                        variant="outlined"
                                        fullWidth
                                        value={startDate}
                                        onChange={(e) => setStartDate(e.target.value)}
                                    />
                                </Grid>

                                <Grid item xs={6} sm={6} >

                                    <Typography variant="h5" color="initial">End Date</Typography>
                                </Grid>

                                <Grid item xs={6} sm={6}>
                                    <FormField
                                        type="date"
                                        variant="outlined"
                                        fullWidth
                                        value={endDate}
                                        onChange={(e) => setEndDate(e.target.value)}
                                    />
                                </Grid>




                                <Grid item xs={12}>
                                    <FormField
                                        label="Applicant Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={applicantDashboardMessage}
                                        onChange={(e) => setApplicantDashboardMessage(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Written Shortlisted Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={writtenShortlistedDashboardMessage}
                                        onChange={(e) => setWrittenShortlistedDashboardMessage(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Written Passed Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={writtenPassedDashboardMessage}
                                        onChange={(e) => setWrittenPassedDashboardMessage(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Technical Viva Passed Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={technicalVivaPassedDashboardMessage}
                                        onChange={(e) => setTechnicalVivaPassedDashboardMessage(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Aptitude Test Passed Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={aptitudeTestPassedDashboardMessage}
                                        onChange={(e) => setAptitudeTestPassedDashboardMessage(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="HR Viva Passed Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={hrVivaPassedDashboardMessage}
                                        onChange={(e) => setHrVivaPassedDashboardMessage(e.target.value)}



                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Trainee Dashboard Message"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={traineeDashboardMessage}
                                        onChange={(e) => setTraineeDashboardMessage(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormField
                                        label="Written Exam Instruction"
                                        variant="outlined"
                                        fullWidth
                                        multiline
                                        rows={4}
                                        value={writtenExamInstruction}
                                        onChange={(e) => setWrittenExamInstruction(e.target.value)}
                                    />
                                </Grid>

                            </Grid>

                            <Grid item xs={12}>
                                <FormControlLabel
                                    control={
                                        <Checkbox
                                            checked={isAvailable}
                                            onChange={(e) => setIsAvailable(e.target.checked)}
                                            color="primary"
                                        />
                                    }
                                    label="Make it available"
                                />
                            </Grid>



                            {showSuccessMessage &&
                                <Alert key="success" variant="success">
                                    {successMessage}
                                </Alert>
                            }

                            {showErrorMessage &&
                                <Alert key="danger" variant="danger">
                                    {errorMessage}
                                </Alert>
                            }

                            <Grid container justifyContent="center">

                                <Grid item xs={6} sm={6} justifyContent="center">
                                    <SubmitButton variant="contained" color="primary" size="large" type="submit" fullWidth>
                                        {buttonText}
                                    </SubmitButton>
                                </Grid>
                            </Grid>

                        </form>
                    </FormContainer>
                </Grid>
            </Box>
        </Box >


    )
}

export default CourseComponent