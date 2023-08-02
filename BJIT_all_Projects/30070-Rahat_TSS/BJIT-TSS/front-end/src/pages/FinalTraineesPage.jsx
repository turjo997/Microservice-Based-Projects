import React, { useContext, useEffect, useState } from 'react'
import { Tab, Tabs, Box, Typography, styled } from '@mui/material';
import { LoginContext } from '../context/LoginContex';
import CourseCards from '../components/CourseCards';
import axios from '../api/axios';
import AptitudeMark from '../components/AptitudeMark';
import CandidateTable from '../components/CandidateTable';
import JSON2Message from '../services/JSON2Message';
import UploadHrVivaMark from '../components/UploadHrVivaMark';
import FinalTraineeTable from '../components/FinalTraineeTable';
import FinalTrainee from '../components/FinalTrainee';


const HeaderTypography = styled(Typography)(({ theme }) => ({
  fontSize: '1.6rem',
  marginBottom: theme.spacing(2),
}));

const FinalTraineesPage = () => {
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("")


  useEffect(() => {
    if (showErrorMessage) {
      console.log(errorMessage);
      setTimeout(() => {
        setErrorMessage("")
        setShowErrorMessage(false)
      }, 2000);
    }
    if (showSuccessMessage) {
      console.log(successMessage);
      setTimeout(() => {
        setSuccessMessage("")
        setShowSuccessMessage(false)
      }, 2000);
    }

  }, [showErrorMessage, showSuccessMessage])

  const { allEvaluators, setAllEvaluators, loggedIn, setCourses, courses, unavailableCourses, setUnavailableCourses } = useContext(LoginContext);


  const [value, setValue] = useState("final-trainees")
  const [value2, setValue2] = useState("")
  const [value3, setValue3] = useState("");

  const handleChange = (event, newValue) => {
    setValue(newValue);
    setValue2("");
    setValue3("")
  };



  const [passedCandidates, setPassedCandidates] = useState([]);

  const [finalTrainees, setfinalTrainees] = useState([])

  const [selectedCourse, setSelectedCourse] = useState({})

  const setSingleCourse = (course) => {
    console.log(course.batchCode);
    setSelectedCourse(course)
    console.log('Select Final');





    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    axios.get(`/api/final-trainee/${course.batchCode}`, config)
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)
          console.log(response?.data?.data?.listResponse);
          setfinalTrainees(response?.data?.data?.listResponse);

        }
      }).catch((error) => {
        console.error('Error getting all passed:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
      });


      axios.get(`/api/final-trainee/all-passed/${course.batchCode}`, config)
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)
          console.log(response?.data?.data?.listResponse);
          setPassedCandidates(response?.data?.data?.listResponse);
        }
      }).catch((error) => {
        console.error('Error getting all passed:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
      });


  }

  const setSingleCourseForUploadMark = (course) => {
    console.log(course);
    setValue2("single-course-update")



    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    axios.get(`/api/final-trainee/all-passed/${course.batchCode}`, config)
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)
          console.log(response?.data?.data?.listResponse);
          setPassedCandidates(response?.data?.data?.listResponse);
        }
      }).catch((error) => {
        console.error('Error getting all  passed:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
      });
  }



  const selectAsTrainee = (candidate) => {



    console.log(candidate);

    const formData = {

      candidateIds: [candidate.candidateId

      ]
    }


    console.log(formData);
    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    axios.post("/api/final-trainee/select", formData, config)
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)
          console.log(response?.data?.data);
          setSingleCourse(selectedCourse);


        }
      }).catch((error) => {
        console.error('Error getting availave:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

      });



  }




  return (
    <Box mt={1}>
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
            <Tab value="final-trainees" label="Final Trainees" />
            <Tab value="upload-mark" label="Select Final Trainiees" />
          </Tabs>
        </Box>
      </Box>

      {value == "final-trainees" && value2 == "" &&

        <Box pt={7}>

          <HeaderTypography>
            Select course to view the final trainees of selected course.
          </HeaderTypography>

          <CourseCards courseButtonText={"Final Trainees for this course"} courses={courses} pathValue={"single-course-candidate"} setValue={setValue2} setSingleCourse={setSingleCourse} />
        </Box>

      }

      {value == "final-trainees" && value2 == "single-course-candidate" &&

        <Box pt={7}>
          <FinalTrainee topMessage={"Final Trainees"} applicants={finalTrainees} showAction={false} />
        </Box>

      }
      {value == "upload-mark" && value2 == "" && value3 == "" &&

        <Box pt={7}>

          <HeaderTypography>
            Select course to select final trainees.
          </HeaderTypography>
          <CourseCards courseButtonText={"Select final trainees"} courses={courses} pathValue={"single-course-candidate"} setValue={setValue2} setSingleCourse={setSingleCourseForUploadMark} />
        </Box>

      }

      {value == "upload-mark" && value2 == "single-course-update" && value3 == "" &&
        <Box pt={7}>



          <FinalTraineeTable applicants={passedCandidates} setApplicants={setPassedCandidates} action={selectAsTrainee} actionText={"Select as trainee"} />
        </Box>
      }




    </Box>

  )
}



export default FinalTraineesPage