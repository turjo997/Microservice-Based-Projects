import React, { useContext, useEffect, useState } from 'react';
import { Container, Row, Col, Form, Button, Alert } from 'react-bootstrap';
import FileService from '../services/FileService';
import { LoginContext } from '../context/LoginContex';
import JSON2Message from '../services/JSON2Message';

const UploadFile = () => {

    const [up1, setUp1] = useState(false)
    const [up2, setUp2] = useState(false)

    const [pageValue, setPageValue] = useState("image-upload");

    useEffect(() => {
        console.log("page value is : " + pageValue);
    }, [pageValue])



    useEffect(() => {
        if (up1 && up2) {
            // setUploaded(true);
            // window.localStorage.setItem("uploadedfortss", "true")
        }
    }, [up1, up2])

    const { setUploaded } = useContext(LoginContext);
    const [selectedImage, setSelectedImage] = useState(null);
    const [selectedCV, setSelectedCV] = useState(null);
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showError, setShowError] = useState(false)
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("")

    useEffect(() => {
        if (showError) {
            console.log(errorMessage);
            setTimeout(() => {
                setErrorMessage("")
                setShowError(false)
            }, 3000);
        }
        if (showSuccessMessage) {
            console.log(successMessage);
            setTimeout(() => {
                setSuccessMessage("")
                setShowSuccessMessage(false)
            }, 3000);
        }

    }, [showSuccessMessage, showError])


    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setSelectedImage(file);
    };

    const handleCVChange = (e) => {
        const file = e.target.files[0];
        setSelectedCV(file);
    };

    // const form = document.querySelector("form");

    const imageUpload = () => {
        const formData = new FormData();
        formData.append('profile-picture', selectedImage);
        const token = window.localStorage.getItem("tss-token");

        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        FileService.uploadImage(formData, config)
            .then((response) => {
                console.log(response.data);

                if (response.status === 201) {
                    console.log("Setup 1 is true");
                    setShowSuccessMessage(true);
                    setSelectedImage(null);
                    console.log(response?.data?.successMessage);
                    setSuccessMessage(response?.data?.successMessage)
                    setUp2(true)

                    setTimeout(() => {
                        setPageValue("cv-upload")

                    }, 2000);
                }
            })
            .catch((error) => {
                console.error('Error uploading files:', error.response.data);

                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
                setShowError(true)

            });

    }

    const cvUpload = () => {
        const formDatacv = new FormData();
        formDatacv.append('resume', selectedCV);


        const token = window.localStorage.getItem("tss-token");

        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        FileService.uploadCV(formDatacv, config)
            .then((response) => {
                console.log(response.data);

                if (response.status === 201) {
                    console.log("Setup 1 is true");
                    setShowSuccessMessage(true);
                    setSelectedCV(null);
                    console.log(response?.data?.successMessage);
                    setSuccessMessage(response?.data?.successMessage)
                    setUp2(true)

                    setUploaded(true);
                }
            })
            .catch((error) => {
                console.error('Error uploading files:', error.response.data);

                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
                setShowError(true)

            });

    }


    return (
        <>

            {pageValue == "image-upload" &&

                <Container>
                    <Row className="justify-content-center mt-3">
                        <Col md={6}>
                            <Form>
                                <Form.Group className="mb-3">
                                    <Form.Label>Upload Image</Form.Label>
                                    <Form.Control type="file" onChange={handleImageChange} accept="image/*" />
                                </Form.Group>


                                {/* Success message */}
                                {showSuccessMessage && (
                                    <Alert variant="success">Profile Pic uploaded successfully!</Alert>
                                )}
                                {errorMessage && (
                                    <Alert variant="danger">{errorMessage}</Alert>
                                )}

                                {(errorMessage && errorMessage === "") ? (
                                    <Alert variant="danger">{errorMessage}</Alert>
                                ) : null}





                                <div className="d-flex justify-content-end mt-3">
                                    <Button variant="primary" onClick={imageUpload}>
                                        Upload
                                    </Button>
                                </div>
                            </Form>
                        </Col>
                    </Row>
                </Container>
            }


            {pageValue == "cv-upload" &&

                <Container>
                    <Row className="justify-content-center mt-3">
                        <Col md={6}>
                            <Form>

                                <Form.Group className="mb-3">
                                    <Form.Label>Upload CV</Form.Label>
                                    <Form.Control type="file" onChange={handleCVChange} accept=".pdf,.doc,.docx" />
                                </Form.Group>

                                {/* Success message */}
                                {showSuccessMessage && (
                                    <Alert variant="success">Files uploaded successfully!</Alert>
                                )}
                                {showError && (
                                    <Alert variant="danger">{errorMessage}</Alert>
                                )}

                                <div className="d-flex justify-content-end mt-3">
                                    <Button variant="primary" onClick={cvUpload}>
                                        Upload
                                    </Button>
                                </div>
                            </Form>
                        </Col>
                    </Row>
                </Container>
            }


        </>
    );
};

export default UploadFile;
