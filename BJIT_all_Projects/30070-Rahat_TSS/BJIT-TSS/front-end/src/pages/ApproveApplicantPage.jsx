import React, { useContext, useEffect, useState } from 'react'
import { Tab, Tabs, Box, Typography, Button } from '@mui/material';
import { LoginContext } from '../context/LoginContex';
import CourseCards from '../components/CourseCards';
import axios from '../api/axios';
import ApplicantTable from '../components/ApplicantTable';
import AllApplicantsTableForApprove from '../components/AllApplicantsTableForApprove';
import { styled } from '@mui/material/styles';



const HeaderTypography = styled(Typography)(({ theme }) => ({
    fontSize: '1.6rem',
    marginBottom: theme.spacing(2),
}));





const ApproveApplicantPage = () => {

    const [allInstitute, setAllInstitute] = useState([])


    const fetchIntitutesList=()=>{
        let role = "APPLICANT"

        const formData = {
            role,
            batchCode
        };
        console.log(formData);
        
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        axios.post(`/api/application/course/distinct_institution`, formData, config)
            .then((response) => {
                console.log(response);
                console.log(response?.data?.data);


                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data?.listResponse);
                    setAllInstitute(response?.data?.data?.listResponse)


                    setTimeout(() => {
                        setShowSuccessMessage(false)
                        setSuccessMessage("")



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



    const fetchApplicant = () => {

        let role = "APPLICANT"

        const formData = {
            role,
            batchCode
        };
        console.log(formData);

        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        axios.post(`/api/application/course`, formData, config)
            .then((response) => {
                console.log(response);
                console.log(response?.data?.data);


                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data?.listResponse);
                    setAllApplicants(response?.data?.data?.listResponse)


      
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

        role = "CANDIDATE"

        const formData2 = {
            role,
            batchCode
        };
        console.log(formData2);

        axios.post(`/api/application/course`, formData2, config)
            .then((response) => {
                console.log(response);
                console.log(response?.data?.data);


                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data?.listResponse);
                    setAllCandidated(response?.data?.data?.listResponse)


          
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
    };



    const approveApplicant = (examinee) => {
        console.log(examinee);

        let b = examinee.courseInfo.batchCode;

        let selectedid = examinee.examineeId

        console.log(selectedid);
        console.log(b);

        const formData = {
            examineeId: selectedid,


        };
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };
        axios
            .post('/api/approval/applicant', formData, config)
            .then((response) => {

                console.log(response);
                fetchApplicant();
                fetchIntitutesList();


            })
            .catch((error) => {
                console.error('Error approving applicant:', error);
            }).finally(() => {
                // setTimeout(() => {

                //     window.location.reload(false);
                // }, 2000);

            })




    };


    const [allApplicants, setAllApplicants] = useState([]);

    const [allCandidated, setAllCandidated] = useState([]);

    const [value2, setValue2] = useState('')

    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("")

    const [batchCode, setBatchCode] = useState("");
    // const [role, setRole] = useState("APPLICANT");

    const setSingleCourse = (c) => {
        setBatchCode(c.batchCode);
    }

    useEffect(() => {
        if (batchCode !== "") {
            console.log(batchCode);
            let role = "APPLICANT"

            const formData = {
                role,
                batchCode
            };
            console.log(formData);

            const token = window.localStorage.getItem("tss-token");
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            };

            axios.post(`/api/application/course`, formData, config)
                .then((response) => {
                    console.log(response);
                    console.log(response?.data?.data);


                    if (response.status === 200) {
                        setShowSuccessMessage(true)
                        setSuccessMessage(response.data.successMessage)
                        console.log(response?.data?.data?.listResponse);
                        setAllApplicants(response?.data?.data?.listResponse)


                        setTimeout(() => {
                            setShowSuccessMessage(false)
                            setSuccessMessage("")



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

            role = "CANDIDATE"

            const formData2 = {
                role,
                batchCode
            };
            console.log(formData2);

            axios.post(`/api/application/course`, formData2, config)
                .then((response) => {
                    console.log(response);
                    console.log(response?.data?.data);


                    if (response.status === 200) {
                        setShowSuccessMessage(true)
                        setSuccessMessage(response.data.successMessage)
                        console.log(response?.data?.data?.listResponse);
                        setAllCandidated(response?.data?.data?.listResponse)


                        setTimeout(() => {
                            setShowSuccessMessage(false)
                            setSuccessMessage("")



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

    }, [batchCode])






    useEffect(() => {

        if (showSuccessMessage) {
            console.log(successMessage);
        }
        if (showErrorMessage) {
            console.log(errorMessage);
        }
    }, [showErrorMessage, showSuccessMessage])



    const handleChange = (event, newValue) => {
        setValue(newValue);
        setValue2("")
    };


    const { allEvaluators, setAllEvaluators, loggedIn, setCourses, courses, unavailableCourses, setUnavailableCourses } = useContext(LoginContext);

    const [value, setValue] = useState('approved-applicants');
    // const [value2, setValue2] = useState('')

    useEffect(() => {
        console.log(value);
        console.log(value2);
    }, [value, value2]);

    const goBackToAllApplicants = () => {
        setValue2("");
    }
    const goBackToApprovedCandidates = () => {
        setValue2("");
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

                        <Tab value="approved-applicants" label="Approved Applicants" />
                        <Tab value="all-applicants" label="All Applicants" />

                    </Tabs>
                </Box>

            </Box>

            {value == "approved-applicants" && value2 == "" &&
                <Box pt={7}>
                    <HeaderTypography>
                        Select course to view approved candidates for selected course.
                    </HeaderTypography>
                    <CourseCards courseButtonText={"Approved Candidates for this course"} courses={courses} pathValue={"single-course-candidate"} setValue={setValue2} setSingleCourse={setSingleCourse} />
                </Box>
            }

            {value == "approved-applicants" && value2 == "single-course-candidate" &&
                <Box pt={7}>
                    <Button onClick={goBackToApprovedCandidates}>Go Back</Button>

                    <ApplicantTable showAction={false} applicants={allCandidated} setApplicants={setAllApplicants} action={approveApplicant} actionText={"Approve Candidate"} />
                </Box>
            }

            {value == "all-applicants" && value2 == "" &&

                <Box pt={7}>

                    <HeaderTypography>
                        Select course to approve from the selected course.
                    </HeaderTypography>

                    <CourseCards courseButtonText={"Approve Applicants for this course"} courses={courses} pathValue={"single-course-applicant"} setValue={setValue2} setSingleCourse={setSingleCourse} />
                </Box>


            }

            {value == "all-applicants" && value2 == "single-course-applicant" &&
                <Box pt={7}>

                    <AllApplicantsTableForApprove fetchApplicant={fetchApplicant} batchCode={batchCode} allInstitute={allInstitute} fetchIntitutesList={fetchIntitutesList} goBack={goBackToAllApplicants} showAction={true} applicants={allApplicants} setApplicants={setAllApplicants} action={approveApplicant} actionText={"Approve Applicant"} />
                </Box>
            }



        </Box>
    )
}

export default ApproveApplicantPage