import React, { useContext, useEffect, useState } from 'react'
import { Tab, Tabs, Box, Typography, styled } from '@mui/material';
import { LoginContext } from '../context/LoginContex';
import CourseCards from '../components/CourseCards';
import axios from '../api/axios';
import AptitudeMark from '../components/AptitudeMark';
import CandidateTable from '../components/CandidateTable';
import UploadAptitudeMark from "../components/UploadAptitudeMark";
import JSON2Message from '../services/JSON2Message';
import InputNumbersComponent from '../components/InputNumbersComponent ';
import AptitudePassed from '../components/AptitudePassed';


const HeaderTypography = styled(Typography)(({ theme }) => ({
  fontSize: '1.6rem',
  marginBottom: theme.spacing(2),
}));

const AptitudeTestPage = () => {
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
  const [value3, setValue3] = useState("");

  const handleChange = (event, newValue) => {
    setValue(newValue);
    setValue2("");
    setValue3("")
  };

  const [allCandidates, setAllCandidates] = useState([])


  const [passedCandidates, setPassedCandidates] = useState([]);

  const setSingleCourse = (course) => {
    console.log(course);
    let roundName = "aptitude";
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
        console.error('Error getting all passed:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
      });


  }

  const setSingleCourseForUploadMark = (course) => {
    console.log(course);
    setValue2("single-course-update")

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
          setAllCandidates(response?.data?.data?.listResponse);
        }
      }).catch((error) => {
        console.error('Error getting all  written:', error);
        setShowErrorMessage(true)
        setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
      });
  }

  const [selectedCandidate, setSelectedCandidate] = useState({})
  const uploadMarkCandidate = (candidate) => {
    console.log(candidate);
    setValue3("upload-mark")
    setSelectedCandidate(candidate)
  }

  const uploadAptitudeMark = (formData) => {
    console.log(formData);





    // let candidateId = candidate.candidateId;


    // const formData = {
    //   candidateId,
    //   marks
    // };
    console.log(formData);
    console.log("Uploading Aptitude mark");

    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    axios.post("/api/evaluation/upload-mark/aptitude", formData, config)
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)
          console.log(response?.data?.data);
        }
      }).catch((error) => {
        console.error('Error uploading written marks:', error);
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
            <Tab value="passed-candidates" label="Aptitude Test Passed Candidates" />
            <Tab value="upload-mark" label="Upload Aptitude Test Mark" />
            <Tab value="upload-mark-credintials" label="Aptitude Test Credintials" />
          </Tabs>
        </Box>
      </Box>

      {value == "passed-candidates" && value2 == "" &&

        <Box pt={7}>

          <HeaderTypography>
            Select course to view the passed candidates of selected course.
          </HeaderTypography>

          <CourseCards courseButtonText={"Aptitude Test passed for this course"} courses={courses} pathValue={"single-course-candidate"} setValue={setValue2} setSingleCourse={setSingleCourse} />
        </Box>

      }

      {value == "passed-candidates" && value2 == "single-course-candidate" &&

        <Box pt={7}>
          <AptitudePassed type={"aptitude"} topMessage={"Candidate who passed aptitude test"} applicants={passedCandidates} showAction={false} />
        </Box>

      }
      {value == "upload-mark" && value2 == "" && value3 == "" &&

        <Box pt={7}>

          <HeaderTypography>
            Select course to upload marks.
          </HeaderTypography>
          <CourseCards courseButtonText={"Upload Aptitude Marks for this course"} courses={courses} pathValue={"single-course-candidate"} setValue={setValue2} setSingleCourse={setSingleCourseForUploadMark} />
        </Box>

      }

      {value == "upload-mark" && value2 == "single-course-update" && value3 == "" &&
        <Box pt={7}>
          <CandidateTable applicants={allCandidates} setApplicants={setAllCandidates} action={uploadMarkCandidate} actionText={"Upload Mark"} />
        </Box>
      }

      {value == "upload-mark" && value2 == "single-course-update" && value3 == "upload-mark" &&
        <Box pt={7}>
          <UploadAptitudeMark candidate={selectedCandidate} />
        </Box>
      }

      {value == "upload-mark-credintials" &&
        <Box pt={7}>

          <HeaderTypography>
            Select aptitude marks credentials
          </HeaderTypography>

          <InputNumbersComponent type="Aptitude" />
        </Box>
      }


    </Box>

  )
}

export default AptitudeTestPage