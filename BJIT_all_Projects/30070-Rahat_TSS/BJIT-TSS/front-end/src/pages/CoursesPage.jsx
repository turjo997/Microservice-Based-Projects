import React, { useContext, useEffect, useState } from 'react';
import { Tab, Tabs, Box, Card, CardContent, CardMedia, Typography, Button, Grid } from '@mui/material';
import { styled } from '@mui/material/styles';
import { Link, useNavigate } from 'react-router-dom';
import { LoginContext } from '../context/LoginContex';
import axios from '../api/axios';
import CourseComponent from '../components/CourseComponent';
import { format } from 'date-fns';
import CourseCards from '../components/CourseCards';
import JSON2Message from '../services/JSON2Message';
import ClipMessage from '../services/ClipMessage';

const HeaderTypography = styled(Typography)(({ theme }) => ({
  fontSize: '1.6rem',
  marginBottom: theme.spacing(2),
}));

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

const AllCourses = () => {

  const navigate = useNavigate();

  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("")

  useEffect(() => {

    if (showSuccessMessage) {
      console.log(successMessage);
    }
    if (showErrorMessage) {
      console.log(errorMessage);
    }
  }, [showErrorMessage, showSuccessMessage])

  const resetFormFields = () => {
    setIsAvailable(true);
    setCourseName('');
    setCourseDescription('');
    setStartDate('');
    setEndDate('');
    setBatchCode('');
    setVacancy('');
    setWrittenExamTime('');
    setApplicantDashboardMessage('You have successfully applied for this course');
    setWrittenShortlistedDashboardMessage('Congratulations. You have shortlisted for the written exam. Your written exam will be held soon');
    setWrittenPassedDashboardMessage('Congratulations. You have passed the written exam. Your aptitude test will be held soon');
    setTechnicalVivaPassedDashboardMessage('Congratulations. You have passed the technical viva. Your HR viva will be held soon');
    setAptitudeTestPassedDashboardMessage('Congratulations. You have passed the aptitude test. Your technical viva will be held soon.');
    setHrVivaPassedDashboardMessage('Congratulations. You have passed the HR viva. Please wait for the final selection');
    setTraineeDashboardMessage('Congratulations. You have been selected as a Trainee.');
    setWrittenExamInstruction("1. Please arrive at the exam venue 30 minutes before the scheduled start time.\n2. Bring your photo ID and this admit card for verification.\n3. The written exam will consist of multiple-choice questions and coding exercises.\n4. You will have 2 hours to complete the exam.\n5. Use only the provided answer sheet for marking your answers.\n6. Read the instructions carefully before attempting each section.\n7. Write your answers clearly and legibly.\n8. Use a black or blue pen for writing.\n9. Electronic devices, calculators, and study materials are not allowed during the exam.\n10. Once the exam starts, you cannot leave the exam hall until it's completed.\n11. Make sure to submit your answer sheet before leaving.\n12. Violation of exam rules may lead to disqualification.\n13. All the best for your exam!")
  };

  const { role, loggedIn, setCourses, courses, unavailableCourses, setUnavailableCourses } = useContext(LoginContext);

  const [value, setValue] = useState('available-courses');
  const [value2, setValue2] = useState("")

  const handleChange = (event, newValue) => {
    setValue(newValue);
    setValue2("")
  };


  const [courseName, setCourseName] = useState('');
  const [courseDescription, setCourseDescription] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [batchCode, setBatchCode] = useState('');
  const [vacancy, setVacancy] = useState('');
  const [applicationDeadline, setApplicationDeadline] = useState('');
  const [writtenExamTime, setWrittenExamTime] = useState('');
  const [applicantDashboardMessage, setApplicantDashboardMessage] = useState('You have successfully applied for this course');
  const [writtenShortlistedDashboardMessage, setWrittenShortlistedDashboardMessage] = useState('Congratulations. You have shortlisted for the written exam. Your written exam will held soon');
  const [writtenPassedDashboardMessage, setWrittenPassedDashboardMessage] = useState('Congratulations. You have passed the written exam. Your aptitude test will held soon');
  const [technicalVivaPassedDashboardMessage, setTechnicalVivaPassedDashboardMessage] = useState('Congratulations. You have passed the technical viva. Your HR viva will held soon');
  const [aptitudeTestPassedDashboardMessage, setAptitudeTestPassedDashboardMessage] = useState('Congratulations. You have passed the aptitude test. Your technical viva will held soon.');
  const [hrVivaPassedDashboardMessage, setHrVivaPassedDashboardMessage] = useState('Congratulations. You have passed the HR viva. Please wait for the final selection');
  const [traineeDashboardMessage, setTraineeDashboardMessage] = useState('Congratulations. You have been selected as a Trainee.');
  const [writtenExamInstruction, setWrittenExamInstruction] = useState("1. Please arrive at the exam venue 30 minutes before the scheduled start time.\n2. Bring your photo ID and this admit card for verification.\n3. The written exam will consist of multiple-choice questions and coding exercises.\n4. You will have 2 hours to complete the exam.\n5. Use only the provided answer sheet for marking your answers.\n6. Read the instructions carefully before attempting each section.\n7. Write your answers clearly and legibly.\n8. Use a black or blue pen for writing.\n9. Electronic devices, calculators, and study materials are not allowed during the exam.\n10. Once the exam starts, you cannot leave the exam hall until it's completed.\n11. Make sure to submit your answer sheet before leaving.\n12. Violation of exam rules may lead to disqualification.\n13. All the best for your exam!")
  const [isAvailable, setIsAvailable] = useState(true);





  const setSingleCourse = (c) => {
    setCourseName(c.courseName)
    setIsAvailable(c.isAvailable);
    setCourseDescription(c.courseDescription);

    setStartDate(format(new Date(c.startDate), 'yyyy-MM-dd'));
    setEndDate(format(new Date(c.endDate), 'yyyy-MM-dd'));
    setWrittenExamTime(format(new Date(c.writtenExamTime), 'yyyy-MM-dd\'T\'HH:mm'));
    setApplicationDeadline(format(new Date(c.applicationDeadline), 'yyyy-MM-dd'));

    setBatchCode(c.batchCode);
    setVacancy(c.vacancy);
    setApplicantDashboardMessage(c.applicantDashboardMessage);
    setWrittenShortlistedDashboardMessage(c.writtenShortlistedDashboardMessage);
    setWrittenPassedDashboardMessage(c.writtenPassedDashboardMessage);
    setTechnicalVivaPassedDashboardMessage(c.technicalVivaPassedDashboardMessage);
    setAptitudeTestPassedDashboardMessage(c.traineeDashboardMessage);
    setHrVivaPassedDashboardMessage(c.hrVivaPassedDashboardMessage);
    setTraineeDashboardMessage(c.traineeDashboardMessage);
    setWrittenExamInstruction(c.writtenExamInstruction);

  }


  const updateCourse = (event) => {
    event.preventDefault();

    const formData = {
      courseName,
      courseDescription,
      startDate,
      endDate,
      batchCode,
      applicationDeadline,
      vacancy,
      writtenExamTime,
      applicantDashboardMessage,
      writtenShortlistedDashboardMessage,
      writtenPassedDashboardMessage,
      technicalVivaPassedDashboardMessage,
      aptitudeTestPassedDashboardMessage,
      hrVivaPassedDashboardMessage,
      traineeDashboardMessage,
      writtenExamInstruction,
      isAvailable
    };
    console.log(formData);

    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    axios.post(`/api/course/update/batch_code/${formData.batchCode}`, formData, config)
      .then((response) => {
        console.log(response);
        console.log(response?.data?.data);

        setSingleCourse(response?.data?.data)
        const courseId = response?.data?.data?.courseId

        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)

          const updatedCourse = { ...formData, courseId }

          const updatedCourseIndex = courses.findIndex((course) => course.courseId === response?.data?.data?.courseId);

          if (updatedCourseIndex !== -1) {
            setCourses((prevCourses) => [
              ...prevCourses.slice(0, updatedCourseIndex),
              updatedCourse,
              ...prevCourses.slice(updatedCourseIndex + 1),
            ]);
          }

          setTimeout(() => {
            setShowSuccessMessage(false)
            setSuccessMessage("")

            if (isAvailable == true) {
              setValue("available-courses")
            } else {
              setValue("unavailable-courses")
            }
            resetFormFields();
          }, 2000);
        }
      })
      .catch((error) => {
        console.error('Error uploading files:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
        setTimeout(() => {
          setShowErrorMessage(false)
          setErrorMessage("")

        }, 5000);

      }).finally(() => {
        setValue2("");
        window.location.reload(false)
        navigate("/course")

      })

  }

  const handleSubmit = (event) => {
    event.preventDefault();

    const formData = {
      courseName,
      courseDescription,
      startDate,
      endDate,
      batchCode,
      applicationDeadline,
      vacancy,
      writtenExamTime,
      applicantDashboardMessage,
      writtenShortlistedDashboardMessage,
      writtenPassedDashboardMessage,
      technicalVivaPassedDashboardMessage,
      aptitudeTestPassedDashboardMessage,
      hrVivaPassedDashboardMessage,
      traineeDashboardMessage,
      writtenExamInstruction,
      isAvailable
    };
    console.log(formData);

    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    if (value === "single-course" && role === "ADMIN") {

      axios.post(`/api/course/update/batch_code/${formData.batchCode}`, formData, config)
        .then((response) => {
          console.log(response);
          console.log(response?.data?.data);

          setSingleCourse(response?.data?.data)
          const courseId = response?.data?.data?.courseId

          if (response.status === 200) {
            setShowSuccessMessage(true)
            setSuccessMessage(response.data.successMessage)

            const updatedCourse = { ...formData, courseId }

            const updatedCourseIndex = courses.findIndex((course) => course.courseId === response?.data?.data?.courseId);

            if (updatedCourseIndex !== -1) {
              setCourses((prevCourses) => [
                ...prevCourses.slice(0, updatedCourseIndex),
                updatedCourse,
                ...prevCourses.slice(updatedCourseIndex + 1),
              ]);
            }

            setTimeout(() => {
              setShowSuccessMessage(false)
              setSuccessMessage("")

              if (isAvailable == true) {
                setValue("available-courses")
              } else {
                setValue("unavailable-courses")
              }
              resetFormFields();
            }, 2000);
          }
        })
        .catch((error) => {
          console.error('Error uploading files:', error);
          setShowErrorMessage(true)
          setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
          setTimeout(() => {
            setShowErrorMessage(false)
            setErrorMessage("")

          }, 5000);

        });

    } else {

      axios.post('/api/course/create', formData, config)
        .then((response) => {
          console.log(response);

          if (response.status === 201) {
            setShowSuccessMessage(true)
            setSuccessMessage(response.data.successMessage)
            setCourses((prevCourses) => [...prevCourses, response.data.data]);

            setTimeout(() => {
              setShowSuccessMessage(false)
              setSuccessMessage("")

              if (isAvailable == true) {
                setValue("available-courses")
              } else {
                setValue("unavailable-courses")
              }
              resetFormFields();
            }, 2000);
          }
        })
        .catch((error) => {
          console.error('Error uploading files:', error);
          setShowErrorMessage(true)
          setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
          setTimeout(() => {
            setShowErrorMessage(false)
            setErrorMessage("")

          }, 5000);
        });
    }
  };

  console.log(role);

  useEffect(() => {

    if (value === "single-course" && role === "ADMIN") {
    } else {
      resetFormFields();
    }
    console.log(value);
  }, [value]);

  return (
    <Box mt={role === "ADMIN" ? 1 : 4}>
      {role === "ADMIN" &&

        <Box sx={{
          width: '100%',
        }} >
          <Box position="fixed" >

            <Tabs
              value={value}
              onChange={handleChange}
              textColor="secondary"
              indicatorColor="secondary"
              aria-label="secondary tabs example"
            >
              <Tab value="available-courses" label="Available Courses" />

              <Tab value="add-course" label="Add Course" />
              <Tab value="unavailable-courses" label="Unavailable Courses" />
            </Tabs>
          </Box>

        </Box>
      }

      {(role !== "ADMIN" || !loggedIn) &&
        <Box pt={role === "ADMIN" ? 7 : 0}>

          <Grid container spacing={2} justifyContent="center">

            {courses &&
              courses?.map((course) => (
                <Grid item key={course.courseId} xs={12} sm={6} md={4}>
                  <Link to={`/course/${course.courseId}`}>

                    <CourseCard>
                      <CourseMedia component="img" image={course.imageUrl} alt={course.title} />
                      <CourseContent>
                        <CourseTitle variant="h6">{course.courseName}</CourseTitle>
                        <CourseDescription variant="body2">{ClipMessage(course.courseDescription, 85)}</CourseDescription>
                      </CourseContent>
                      <EnrollButton sx={{ bgcolor: "#2d2c72" }} variant="contained" color="primary" size="small">
                        Enroll Now
                      </EnrollButton>
                    </CourseCard>
                  </Link>

                </Grid>
              ))}
          </Grid>

        </Box>

      }

      {value2 === "single-course" && role === "ADMIN" &&
        <Box pt={7}>
          <HeaderTypography>
            Update this course.
          </HeaderTypography>
          <CourseComponent buttonText={"Update Course"} handleSubmit={updateCourse} courseName={courseName} setCourseName={setCourseName} batchCode={batchCode} setBatchCode={setBatchCode} isAvailable={isAvailable} setIsAvailable={setIsAvailable} showSuccessMessage={showSuccessMessage} successMessage={successMessage} showErrorMessage={showErrorMessage} errorMessage={errorMessage} courseDescription={courseDescription} setCourseDescription={setCourseDescription} vacancy={vacancy} setVacancy={setVacancy} applicationDeadline={applicationDeadline} setApplicationDeadline={setApplicationDeadline} writtenExamTime={writtenExamTime} setWrittenExamTime={setWrittenExamTime} startDate={startDate} setStartDate={setStartDate} endDate={endDate} setEndDate={setEndDate} applicantDashboardMessage={applicantDashboardMessage} setApplicantDashboardMessage={setApplicantDashboardMessage} writtenShortlistedDashboardMessage={writtenShortlistedDashboardMessage} setWrittenShortlistedDashboardMessage={setWrittenShortlistedDashboardMessage} writtenPassedDashboardMessage={writtenPassedDashboardMessage} setWrittenPassedDashboardMessage={setWrittenPassedDashboardMessage} technicalVivaPassedDashboardMessage={technicalVivaPassedDashboardMessage} setTechnicalVivaPassedDashboardMessage={setTechnicalVivaPassedDashboardMessage} aptitudeTestPassedDashboardMessage={aptitudeTestPassedDashboardMessage} setAptitudeTestPassedDashboardMessage={setAptitudeTestPassedDashboardMessage} hrVivaPassedDashboardMessage={hrVivaPassedDashboardMessage} setHrVivaPassedDashboardMessage={setHrVivaPassedDashboardMessage} traineeDashboardMessage={traineeDashboardMessage} setTraineeDashboardMessage={setTraineeDashboardMessage} writtenExamInstruction={writtenExamInstruction} setWrittenExamInstruction={setWrittenExamInstruction} />
        </Box>

      }



      {
        value === "available-courses" && value2 == "" && role === "ADMIN" &&

        <Box pt={7}>
          <HeaderTypography>
            Select available course to update.
          </HeaderTypography>

          <CourseCards courseButtonText={"Update Details"} courses={courses} pathValue={"single-course"} setValue={setValue2} setSingleCourse={setSingleCourse} />
        </Box>
      }

      {value === "add-course" && role === "ADMIN" &&
        <Box pt={7}>

          <HeaderTypography>
            Add a new course
          </HeaderTypography>
          <CourseComponent buttonText={"Add Course"} handleSubmit={handleSubmit} courseName={courseName} setCourseName={setCourseName} batchCode={batchCode} setBatchCode={setBatchCode} isAvailable={isAvailable} setIsAvailable={setIsAvailable} showSuccessMessage={showSuccessMessage} successMessage={successMessage} showErrorMessage={showErrorMessage} errorMessage={errorMessage} courseDescription={courseDescription} setCourseDescription={setCourseDescription} vacancy={vacancy} setVacancy={setVacancy} applicationDeadline={applicationDeadline} setApplicationDeadline={setApplicationDeadline} writtenExamTime={writtenExamTime} setWrittenExamTime={setWrittenExamTime} startDate={startDate} setStartDate={setStartDate} endDate={endDate} setEndDate={setEndDate} applicantDashboardMessage={applicantDashboardMessage} setApplicantDashboardMessage={setApplicantDashboardMessage} writtenShortlistedDashboardMessage={writtenShortlistedDashboardMessage} setWrittenShortlistedDashboardMessage={setWrittenShortlistedDashboardMessage} writtenPassedDashboardMessage={writtenPassedDashboardMessage} setWrittenPassedDashboardMessage={setWrittenPassedDashboardMessage} technicalVivaPassedDashboardMessage={technicalVivaPassedDashboardMessage} setTechnicalVivaPassedDashboardMessage={setTechnicalVivaPassedDashboardMessage} aptitudeTestPassedDashboardMessage={aptitudeTestPassedDashboardMessage} setAptitudeTestPassedDashboardMessage={setAptitudeTestPassedDashboardMessage} hrVivaPassedDashboardMessage={hrVivaPassedDashboardMessage} setHrVivaPassedDashboardMessage={setHrVivaPassedDashboardMessage} traineeDashboardMessage={traineeDashboardMessage} setTraineeDashboardMessage={setTraineeDashboardMessage} writtenExamInstruction={writtenExamInstruction} setWrittenExamInstruction={setWrittenExamInstruction} />
        </Box>
      }

      {value === "unavailable-courses" && value2 == "" && role === "ADMIN" &&
        <Box pt={7}>
          <HeaderTypography>
            Select unavailable course to update.
          </HeaderTypography>
          <CourseCards courseButtonText={"Update Details"} courses={unavailableCourses} pathValue={"single-course"} setValue={setValue2} setSingleCourse={setSingleCourse} />
        </Box>
      }
    </Box >
  );
};

export default AllCourses;