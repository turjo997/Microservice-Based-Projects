import React, { useContext, useEffect, useState } from 'react';
import { Tab, Tabs, Box, Card, CardContent, CardMedia, Typography, Button, Grid } from '@mui/material';

import { styled } from '@mui/material/styles';

import { LoginContext } from '../context/LoginContex';
import axios from '../api/axios';

import JSON2Message from '../services/JSON2Message';
import AddEvaluatorForm from '../components/AddEvaluatorForm';
import EvaluatorTable from '../components/EvaluatorTable';
import ApplicantTable from '../components/ApplicantTable'
import ApplicantTableList from '../components/ApplicantTableList';
import CourseCards from '../components/CourseCards';


const HeaderTypography = styled(Typography)(({ theme }) => ({
    fontSize: '1.6rem',
    marginBottom: theme.spacing(2),
}));







const EvaluatorManagement = () => {
    const [allAvailableApplicants, setallAvailableApplicants] = useState([])


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



    const [requestedEvaluatorId, setRequestedEvaluatorId] = useState(0);
    const [assignedApplicants, setAssignedApplicants] = useState([])

    const [searchedEvaluator, setSearchedEvaluator] = useState({});



    const showCourseComponent = (evaluator) => {
        setRequestedEvaluatorId(evaluator.evaluatorId);
        console.log(evaluator.evaluatorId);
        setValue2("courses-for-assigining")
        setSearchedEvaluator(evaluator);
        setValue3("")


        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        // axios.get(`/api/evaluator/assigned-candidates/${evaluator.evaluatorId}`, config)
        // .then((response) => {
        //     console.log(response);
        //     if (response.status === 200) {
        //         setShowSuccessMessage(true)
        //         setSuccessMessage(response.data.successMessage)
        //         console.log(response?.data?.data?.listResponse);
        //         setAssignedApplicants(response?.data?.data?.listResponse)
        //     }
        // }).catch((error) => {
        //     console.error('Error uploading written marks:', error);
        //     setShowErrorMessage(true)
        //     setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
        // });


    }

    const showAssignedEvaluator = (evaluator) => {
        setRequestedEvaluatorId(evaluator.evaluatorId);
        console.log(evaluator.evaluatorId);
        setValue2("single-evaluator-applicant")
        setSearchedEvaluator(evaluator);


        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        axios.get(`/api/evaluator/assigned-candidates/${evaluator.evaluatorId}`, config)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data?.listResponse);
                    setAssignedApplicants(response?.data?.data?.listResponse)
                }
            }).catch((error) => {
                console.error('Error uploading written marks:', error);
                setShowErrorMessage(true)
                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
            });
    }


    const [value2, setValue2] = useState('')

    const { allEvaluators, setAllEvaluators, role, loggedIn, setCourses, courses, unavailableCourses, setUnavailableCourses } = useContext(LoginContext);

    const [value, setValue] = useState('all-evaluators');
    const [value3, setValue3] = useState("")

    const handleChange = (event, newValue) => {
        setValue(newValue);
        setValue2("")
        setValue3("")
    };








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
            isAvailable
        };
        console.log(formData);

        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };


    }
    const [batchCode, setBatchCode] = useState("");
    const [selectedCourse, setSelectedCourse]= useState({})

    const setCourseAndShowAvailableApplicants = (course) => {

        setSelectedCourse(course)
        console.log(course);
        setBatchCode(course.batchCode)

        let role = "CANDIDATE"

        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        const formData = {
            role,
            batchCode: course.batchCode
        };
        console.log(formData);

        axios.post("/api/application/course/unassigned-candidates", formData, config)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data);
                    setallAvailableApplicants(response?.data?.data?.listResponse)


                }
            }).catch((error) => {
                console.error('Error getting availave:', error);
                setShowErrorMessage(true)
                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

            });

    }



    useEffect(() => {
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };


    }, [])




    useEffect(() => {

        if (value === "single-course") {

        } else {
            // resetFormFields();
        }

        console.log(value);

    }, [value]);


    const assignCandidateToEvaluator = (applicant, evaluator) => {

        console.log(applicant);
        console.log(evaluator);
        const formData = {
            evaluatorId: evaluator.evaluatorId,
            candidateIds: [applicant.candidateId]
        };

        console.log(formData);
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        axios.post("/api/evaluation/assign-answer", formData, config)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data);
                    setCourseAndShowAvailableApplicants(selectedCourse);

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
                        <Tab value="all-evaluators" label="All Evaluators" />

                        <Tab value="add-evaluator" label="Add Evaluator" />
                        <Tab value="assign-evaluator" label="Assign Evaluator" />
                    </Tabs>
                </Box>

            </Box>



            {value === "all-evaluators" && value2 == "" &&
                <Box pt={7}>
                    <EvaluatorTable topMessage={"Select Evaluator to view assigined candidates to him"} data={allEvaluators} onRowClick={showAssignedEvaluator} />
                </Box>
            }


            {value == "all-evaluators" && value2 == "single-evaluator-applicant" &&
                <Box pt={7}>

                    <ApplicantTableList
                        evaluator={searchedEvaluator}
                        topMessage={`${searchedEvaluator.name} has been assigned to these candidates.`}
                        applicants={assignedApplicants}
                        setApplicants={setAssignedApplicants}
                        actionText={"Aassigned Applicant"} />
                </Box>
            }


            {value === "add-evaluator" &&
                <AddEvaluatorForm setAllEvaluators={setAllEvaluators} setValue={setValue} />

            }



            {value === "assign-evaluator" && value2 == "" &&
                <Box pt={7}>
                    <EvaluatorTable topMessage={"Select Evaluator to assigin candidates"} data={allEvaluators} onRowClick={showCourseComponent} />
                </Box>
            }





            {value == "assign-evaluator" && value2 == "courses-for-assigining" && value3 == "" &&
                <Box pt={7}>

                    <HeaderTypography>
                        Select course for selecting candidate for assigning to <b>{searchedEvaluator.name} </b>
                    </HeaderTypography>


                    <CourseCards courseButtonText={"Candidates of this course"}  courses={courses} setValue={setValue3} setSingleCourse={setCourseAndShowAvailableApplicants} pathValue={"applicants-for-assigining"} />


                    {/* <ApplicantTable applicants={allApplicants} setApplicants={setAllApplicants} action={approveApplicant} actionText={"Approve Applicant"} /> */}
                </Box>
            }


            {value == "assign-evaluator" && value2 == "courses-for-assigining" && value3 == "applicants-for-assigining" &&

                <Box pt={7}>

                    <ApplicantTableList
                    showHidden={true}
                        evaluator={searchedEvaluator}
                        topMessage={`Assigne candidats for ${searchedEvaluator.name}.`}
                        applicants={allAvailableApplicants}
                        setApplicants={setallAvailableApplicants}
                        actionText={"Assigne"}
                        action={assignCandidateToEvaluator}

                        showAction={true}
                    />

                </Box>
            }








        </Box >
    );
};

export default EvaluatorManagement