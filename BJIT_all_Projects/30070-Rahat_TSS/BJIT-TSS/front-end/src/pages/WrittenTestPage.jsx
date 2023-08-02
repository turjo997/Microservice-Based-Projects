import React, { useContext, useEffect, useState } from 'react'
import { Tab, Tabs, Box, Typography, styled } from '@mui/material';
import { LoginContext } from '../context/LoginContex';
import CourseCards from '../components/CourseCards';
import axios from '../api/axios';
import ApplicantTable from '../components/ApplicantTable';
import AptitudeMark from '../components/AptitudeMark';
import WrittenMark from '../components/WrittenMark';
import InputNumbersComponent from '../components/InputNumbersComponent ';


const HeaderTypography = styled(Typography)(({ theme }) => ({
  fontSize: '1.6rem',
  marginBottom: theme.spacing(2),
}));

const WrittenTestPage = () => {
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


  const [value, setValue] = useState("passed-candidates")
  const [value2, setValue2] = useState("")

  const handleChange = (event, newValue) => {
    setValue(newValue);
    setValue2("");
  };

  const [allCandidates, setAllCandidates] = useState([])
  const [passedCandidates, setPassedCandidates] = useState([]);

  const setSingleCourse = (course) => {
    console.log(course);
    let roundName = "written";
    let batchCode = course.batchCode;

    const formData = {
      roundName,
      batchCode

    };

    console.log(formData);
    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    axios.post("/api/evaluation/passed-round", formData, config)
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)
          console.log(response?.data?.data?.listResponse);
          setPassedCandidates(response?.data?.data?.listResponse);
        }
      }).catch((error) => {
        console.error('Error getting all passed written:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
      });


  }

  const setSingleCourseForUploadMark = (course) => {
    console.log(course);
    setValue2("single-course-update")

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
            <Tab value="passed-candidates" label="Written Test Passed Candidates" />
            <Tab value="upload-mark" label="Written Test Cradentials" />
          </Tabs>
        </Box>
      </Box>

      {value == "passed-candidates" && value2 == "" &&

        <Box pt={7}>

          <HeaderTypography>
            Select course to view the passed candidates of selected course.
          </HeaderTypography>

          <CourseCards courseButtonText={"Written Test passed for this course"} courses={courses} pathValue={"single-course-candidate"} setValue={setValue2} setSingleCourse={setSingleCourse} />
        </Box>

      }

      {value == "passed-candidates" && value2 == "single-course-candidate" &&

        <Box pt={7}>

          <WrittenMark topMessage={"Candidate who passed written test"} applicants={passedCandidates} showAction={false} />
        </Box>

      }
      {value == "upload-mark" && value2 == "" &&

        <Box pt={7}>

          <HeaderTypography>
            Select written marks credentials
          </HeaderTypography>

          <InputNumbersComponent type="Written" />
        </Box>

      }




    </Box>

  )
}


export default WrittenTestPage