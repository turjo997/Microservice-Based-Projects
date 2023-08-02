import React, { useContext, useEffect, useState } from 'react';
import { Container, Row, Col, Form, Button, Alert } from 'react-bootstrap';
import axios from '../api/axios';
import { useNavigate } from 'react-router-dom/dist/index';
import { LoginContext } from '../context/LoginContex';
import JSON2Message from '../services/JSON2Message';


const RegistrationForm = () => {

 
    
    const [pageNumber, setPageNumber] = useState(1);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        gender: '',
        dateOfBirth: '',
        email: '',
        contactNumber: '',
        degreeName: '',
        educationalInstitute: '',
        cgpa: '',
        passingYear: '',
        presentAddress: '',
        password: '',
    });
    const [errorMessage, setErrorMessage] = useState("");
    const [apiResponse, setApiResponse] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [data, setData] = useState(null);
    const [successMessage, setSuccessMessage] = useState("")
    const { setLoggedIn, setRole, uploaded, setUploaded, setUserData } = useContext(LoginContext);
    const navigate = useNavigate();

    useEffect(() => {
      console.log(uploaded);
    }, [uploaded])

    useEffect(() => {
        console.log(uploaded);
      }, [])
    

    useEffect(() => {
        if (successMessage!=="") {
            console.log(successMessage);
        }
        if (errorMessage !== "") {
            console.log(errorMessage);
            
        }
     
    }, [successMessage, errorMessage])

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
    };

    const handleDateChange = (date) => {
        setFormData((prevFormData) => ({ ...prevFormData, dateOfBirth: date }));
    };

    const handlePassingYearChange = (e) => {
        setFormData((prevFormData) => ({ ...prevFormData, passingYear: e.target.value }));
    };

    const handleDegreeChange = (e) => {
        setFormData((prevFormData) => ({ ...prevFormData, degreeName: e.target.value }));
    };

    const degrees = ['BSc', 'BA', 'MSc', 'MA', 'BCom', 'MCom', 'HSC', 'Other'];

    const passingYears = [];
    const currentYear = new Date().getFullYear();
    for (let i = 0; i < 10; i++) {
        passingYears.push(currentYear + i - 8);
    };
    const isPageOneValid = () => {
        return formData.firstName !== '' && formData.lastName !== '' && formData.gender !== '' && formData.dateOfBirth !== '';
    };

    const isPageTwoValid = () => {
        return formData.email !== '' && formData.contactNumber !== '' && formData.degreeName !== '';
    };

    const isPageThreeValid = () => {
        return formData.cgpa !== '' && formData.passingYear !== '' && formData.presentAddress !== '' && formData.educationalInstitute !== '';
    };

    useEffect(() => {
        if (data && data.status === 201) {
            setSuccessMessage(data.data.successMessage)
            console.log(successMessage);
    
            window.localStorage.setItem("tss-token", data.data.data.token)
            console.log(data.data.data.token);
    
            setTimeout(() => {
                setSuccessMessage(null);
                setLoggedIn(true)
                setRole("USER")
                setUploaded(false)
    
            }, 2000);
        } else if (error) {
            setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)) );
            setPageNumber(1);
    
            setTimeout(() => {
                setErrorMessage("");
    
            }, 8000);
        }
    }, [data, error]);
    



    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission here (e.g., send data to backend server)
        console.log(formData);
        setLoading(true);
        axios.post("/api/auth/register/applicant", formData).then((response) => {
            console.log(response);
            console.log(response?.data?.data?.userInfo);
            setUserData(response?.data?.data?.userInfo)
            setData(response)
            setApiResponse(data.data);
        }).catch((err) => {
            setError(err);
        }).finally(() => {

            setLoading(false);
            // console.log("Posting Finished ");


        })


        // Reset form after submission
        setFormData({
            firstName: '',
            lastName: '',
            gender: '',
            dateOfBirth: '',
            email: '',
            contactNumber: '',
            degreeName: '',
            educationalInstitute: '',
            cgpa: '',
            passingYear: '',
            presentAddress: '',
            password: '',
        });


    };


    const [showAlert, setShowAlert] = useState(false);

    const handleNext = () => {
        let isValid = true;

        if (pageNumber === 1 && !isPageOneValid()) {
            isValid = false;
        }
        if (pageNumber === 2 && !isPageTwoValid()) {
            isValid = false;
        }
        if (pageNumber === 3 && !isPageThreeValid()) {
            isValid = false;
        }

        if (isValid) {
            setPageNumber(pageNumber + 1);
        } else {
            setShowAlert(true);
            setTimeout(() => setShowAlert(false), 2000); // Dismiss alert after 2 seconds
        }
    };

    const handleBack = () => {
        setPageNumber(pageNumber - 1);
    };


    return (
        <Container>
            <Row className="justify-content-center mt-3">
                <Col md={6}>
                    <Form onSubmit={handleSubmit}>
                        {pageNumber === 1 && (
                            <>
                                <Form.Group className="mb-3" controlId="firstName">
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        name="firstName"
                                        value={formData.firstName}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="lastName">
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        name="lastName"
                                        value={formData.lastName}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="gender">
                                    <Form.Label>Gender</Form.Label>
                                    <Form.Select
                                        required
                                        name="gender"
                                        value={formData.gender}
                                        onChange={handleChange}
                                    >
                                        <option value="">Select</option>
                                        <option value="male">Male</option>
                                        <option value="female">Female</option>
                                        <option value="other">Other</option>
                                    </Form.Select>
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="dateOfBirth">
                                    <Form.Label>Date of Birth</Form.Label>
                                    <Form.Control
                                        required
                                        type="date"
                                        name="dateOfBirth"
                                        value={formData.dateOfBirth}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </>
                        )}
                        {pageNumber === 2 && (
                            <>
                                <Form.Group className="mb-3" controlId="email">
                                    <Form.Label>Email Address</Form.Label>
                                    <Form.Control
                                        required
                                        type="email"
                                        name="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="password">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control
                                        required
                                        type="password"
                                        name="password"
                                        value={formData.password}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="contactNumber">
                                    <Form.Label>Contact Number</Form.Label>
                                    <Form.Control
                                        required
                                        type="number"
                                        name="contactNumber"
                                        value={formData.contactNumber}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="degreeName">
                                    <Form.Label>Degree Name</Form.Label>
                                    <Form.Select
                                        required
                                        name="degreeName"
                                        value={formData.degreeName}
                                        onChange={handleDegreeChange}
                                    >
                                        <option value="">Select</option>
                                        {degrees.map((degree) => (
                                            <option key={degree} value={degree}>
                                                {degree}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                            </>
                        )}
                        {pageNumber === 3 && (
                            <>
                                <Form.Group className="mb-3" controlId="educationalInstitute">
                                    <Form.Label>Educational Institute</Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        name="educationalInstitute"
                                        value={formData.educationalInstitute}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="cgpa">
                                    <Form.Label>CGPA</Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        name="cgpa"
                                        value={formData.cgpa}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="passingYear">
                                    <Form.Label>Passing Year</Form.Label>
                                    <Form.Select
                                        required
                                        name="passingYear"
                                        value={formData.passingYear}
                                        onChange={handlePassingYearChange}
                                    >
                                        <option value="">Select</option>
                                        {passingYears.map((year) => (
                                            <option key={year} value={year}>
                                                {year}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="presentAddress">
                                    <Form.Label>Present Address</Form.Label>
                                    <Form.Control
                                        required
                                        as="textarea"
                                        name="presentAddress"
                                        value={formData.presentAddress}
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </>
                        )}

                        {/* Error message */}
                        {showAlert && (
                            <Alert variant="danger">Please complete all fields in Page {pageNumber}.</Alert>
                        )}
                        {successMessage &&
                            <Alert key="success" variant="success">
                                {successMessage}
                            </Alert>
                        }
                        {

                            !successMessage && (

                                errorMessage &&
                                <Alert key="success" variant="danger">
                                    {errorMessage}
                                </Alert>
                            )
                        }

                        {/* Next and Back buttons */}
                        <div className="d-flex justify-content-between mt-3">
                            {pageNumber !== 1 && (
                                <Button variant="secondary" onClick={handleBack}>
                                    Back
                                </Button>
                            )}
                            {pageNumber !== 3 ? (
                                <Button variant="primary" onClick={handleNext}>
                                    Next
                                </Button>
                            ) : (
                                <Button type="submit" variant="primary">
                                    Register
                                </Button>
                            )}
                        </div>


                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default RegistrationForm;
