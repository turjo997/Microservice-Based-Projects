import React, { useEffect, useState } from 'react';
import { TextField, Grid, Paper, Button } from '@mui/material';
import axios from '../api/axios';
import { Alert } from 'react-bootstrap';
import JSON2Message from '../services/JSON2Message';

const InputNumbersComponent = (type) => {
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("")

    const [totalMarkWritten, setTotalMarkWritten] = useState(10);
    const [writtenQuestionNumber, setWrittenQuestionNumber] = useState(5);
    const [passingMarkWritten, setPassingMarkWritten] = useState(5);

    const [totalMarkAptitude, setTotalMarkAptitude] = useState(10);
    const [aptitudeQuestionNumber, setAptitudeQuestionNumber] = useState(5);
    const [passingMarkAptitude, setPassingMarkAptitude] = useState(5);

    const [totalMarkTechnical, setTotalMarkTechnical] = useState(10);
    const [technicalQuestionNumber, setTechnicalQuestionNumber] = useState(5);
    const [passingMarkTechnical, setPassingMarkTechnical] = useState(5);

    const [totalMarkHr, setTotalMarkHr] = useState(10);
    const [hrQuestionNumber, setHrQuestionNumber] = useState(5);
    const [passingMarkHr, setPassingMarkHr] = useState(5);

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



    useEffect(() => {
        let formData = []

        if (type.type === "Written") {
            formData =
                [
                    {
                        dataKey: "TotalMarkWritten"
                    },
                    {
                        dataKey: "PassingMarkWritten"
                    },
                    {
                        dataKey: "WrittenQuestionNumber"
                    }
                ]
        }
        if (type.type === "Aptitude") {
            formData =
                [
                    {
                        dataKey: "TotalMarkAptitude"
                    },
                    {
                        dataKey: "AptitudeQuestionNumber"
                    },
                    {
                        dataKey: "PassingMarkAptitude"
                    }
                ]
        }
        if (type.type === "HR Viva") {
            formData = [
                {
                    dataKey: 'TotalMarkHrViva',
                },
                {
                    dataKey: 'HrVivaQuestionNumber',
                },
                {
                    dataKey: 'PassingMarkHrViva',
                },
            ];
        }

        if (type.type === "Technical Viva") {
            formData = [
                {
                    dataKey: 'TotalMarkTechnical',
                    dataValue: totalMarkTechnical,
                },
                {
                    dataKey: 'TechnicalQuestionNumber',
                    dataValue: technicalQuestionNumber, // Add this line to include the value of technicalVivaQuestionNumber
                },
                {
                    dataKey: 'PassingMarkTechnical',
                    dataValue: passingMarkTechnical, // Add this line to include the value of passingMarkTechnicalViva
                },
            ];
        }

        console.log(formData);
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };
        axios.post("/api/data-storage/get", formData, config)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data?.listResponse);

                    for (let index = 0; index < response?.data?.data?.listResponse.length; index++) {
                        const element = response?.data?.data?.listResponse[index];
                        console.log(element.dataKey);
                        if (element.dataKey == "TotalMarkWritten") {
                            console.log(element.dataValue);
                            setTotalMarkWritten(element.dataValue)
                        } else if (element.dataKey == "PassingMarkWritten") {
                            console.log(element.dataValue);

                            setPassingMarkWritten(element.dataValue)
                        } else if (element.dataKey == "WrittenQuestionNumber") {
                            console.log(element.dataValue);

                            setWrittenQuestionNumber(element.dataValue)
                        } else if (element.dataKey == "TotalMarkAptitude") {
                            console.log(element.dataValue);

                            setTotalMarkAptitude(element.dataValue)
                        } else if (element.dataKey == "AptitudeQuestionNumber") {
                            console.log(element.dataValue);

                            setAptitudeQuestionNumber(element.dataValue)
                        } else if (element.dataKey == "PassingMarkAptitude") {
                            console.log(element.dataValue);

                            setPassingMarkAptitude(element.dataValue)
                        } else if (element.dataKey == "TotalMarkTechnical") {
                            console.log(element.dataValue);

                            setTotalMarkTechnical(element.dataValue)
                        } else if (element.dataKey == "PassingMarkTechnical") {
                            console.log(element.dataValue);

                            setPassingMarkTechnical(element.dataValue)
                        } else if (element.dataKey == "TechnicalQuestionNumber") {
                            console.log(element.dataValue);

                            setTechnicalQuestionNumber(element.dataValue)
                        } else if (element.dataKey == "TotalMarkHrViva") {
                            console.log(element.dataValue);

                            setTotalMarkHr(element.dataValue)
                        } else if (element.dataKey == "PassingMarkHrViva") {
                            console.log(element.dataValue);

                            setPassingMarkHr(element.dataValue)
                        } else if (element.dataKey == "HrVivaQuestionNumber") {
                            console.log(element.dataValue);

                            setHrQuestionNumber(element.dataValue)
                        } else {
                            console.log(element);
                        }
                    }
                }
            }).catch((error) => {
                console.error('Error getting availave:', error);
                setShowErrorMessage(true)
                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

            });


    }, [])

    useEffect(() => {
        console.log(totalMarkWritten);
    }, [totalMarkWritten])


    const handleTotalMarkWrittenChange = (event) => {
        console.log(event.target.value);
        setTotalMarkWritten(event.target.value);
    };

    const handleWrittenQuestionNumberChange = (event) => {
        setWrittenQuestionNumber(event.target.value);
    };

    const handlePassingMarkWrittenChange = (event) => {
        setPassingMarkWritten(event.target.value);
    };
    const handleTotalMarkAptitudeChange = (event) => {
        setTotalMarkAptitude(event.target.value);
    };

    const handleAptitudeQuestionNumberChange = (event) => {
        setAptitudeQuestionNumber(event.target.value);
    };

    const handlePassingMarkAptitudeChange = (event) => {
        setPassingMarkAptitude(event.target.value);
    };

    // Additional event handlers for "HR" type
    const handleTotalMarkHrChange = (event) => {
        setTotalMarkHr(event.target.value);
    };

    const handleHrQuestionNumberChange = (event) => {
        setHrQuestionNumber(event.target.value);
    };

    const handlePassingMarkHrChange = (event) => {
        setPassingMarkHr(event.target.value);
    };
    const handleTotalMarkTechnicalVivaChange = (event) => {
        setTotalMarkTechnical(event.target.value);
    };

    const handleTechnicalVivaQuestionNumberChange = (event) => {
        setTechnicalQuestionNumber(event.target.value);
    };

    const handlePassingMarkTechnicalVivaChange = (event) => {
        setPassingMarkTechnical(event.target.value);
    };



    const handleSubmit = () => {
        console.log(type.type);
        let formData = []
        if (type.type === "Written") {
            console.log(totalMarkWritten);
            formData =
                [
                    {
                        dataKey: "TotalMarkWritten",
                        dataValue: totalMarkWritten,
                    },
                    {
                        dataKey: "PassingMarkWritten",
                        dataValue: passingMarkWritten,
                    },
                    {
                        dataKey: "WrittenQuestionNumber",
                        dataValue: writtenQuestionNumber,
                    }
                ]
        }

        if (type.type === "Aptitude") {
            formData =
                [
                    {
                        dataKey: "TotalMarkAptitude",
                        dataValue: totalMarkAptitude,
                    },
                    {
                        dataKey: "AptitudeQuestionNumber",
                        dataValue: passingMarkAptitude,
                    },
                    {
                        dataKey: "PassingMarkAptitude",
                        dataValue: aptitudeQuestionNumber,
                    }
                ]
        }
        if (type.type === "HR Viva") {
            formData = [
                {
                    dataKey: "TotalMarkHrViva",
                    dataValue: totalMarkHr,
                },
                {
                    dataKey: "HrVivaQuestionNumber",
                    dataValue: hrQuestionNumber,
                },
                {
                    dataKey: "PassingMarkHrViva",
                    dataValue: passingMarkHr,
                },
            ];
        }

        if (type.type === "Technical Viva") {
            formData = [
                {
                    dataKey: 'TotalMarkTechnical', 
                    dataValue: totalMarkTechnical, 
                },
                {
                    dataKey: 'TechnicalQuestionNumber', // Updated dataKey for Technical Viva
                    dataValue: technicalQuestionNumber, // Add this line to include the value of technicalQuestionNumber
                },
                {
                    dataKey: 'PassingMarkTechnical', // Updated dataKey for Technical Viva
                    dataValue: passingMarkTechnical, // Add this line to include the value of passingMarkTechnical
                },
            ];
        }


        console.log(formData);
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };
        axios.post("/api/data-storage/set", formData, config)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data);


                }
            }).catch((error) => {
                console.error('Error getting availave:', error);
                setShowErrorMessage(true)
                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

            });



    }

    return (
        <Paper elevation={3} sx={{ padding: 2 }}>

            {type.type === "Written" &&

                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Total Mark (${type.type})`}
                            type="number"
                            value={totalMarkWritten}
                            onChange={handleTotalMarkWrittenChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`${type.type} Question Number`}
                            type="number"
                            value={writtenQuestionNumber}
                            onChange={handleWrittenQuestionNumberChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Passing Mark (${type.type})`}
                            type="number"
                            value={passingMarkWritten}
                            onChange={handlePassingMarkWrittenChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" fullWidth onClick={handleSubmit}>
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            }
            {type.type === 'Aptitude' && (
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Total Mark (${type.type})`}
                            type="number"
                            value={totalMarkAptitude}
                            onChange={handleTotalMarkAptitudeChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`${type.type} Question Number`}
                            type="number"
                            value={aptitudeQuestionNumber}
                            onChange={handleAptitudeQuestionNumberChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Passing Mark (${type.type})`}
                            type="number"
                            value={passingMarkAptitude}
                            onChange={handlePassingMarkAptitudeChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" fullWidth onClick={handleSubmit}>
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            )}

            {type.type === 'HR Viva' && (
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Total Mark (${type.type})`}
                            type="number"
                            value={totalMarkHr}
                            onChange={handleTotalMarkHrChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`${type.type} Question Number`}
                            type="number"
                            value={hrQuestionNumber}
                            onChange={handleHrQuestionNumberChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Passing Mark (${type.type})`}
                            type="number"
                            value={passingMarkHr}
                            onChange={handlePassingMarkHrChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" fullWidth onClick={handleSubmit}>
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            )}

            {type.type === 'Technical Viva' && (
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Total Mark (${type.type})`}
                            type="number"
                            value={totalMarkTechnical}
                            onChange={handleTotalMarkTechnicalVivaChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`${type.type} Question Number`}
                            type="number"
                            value={technicalQuestionNumber}
                            onChange={handleTechnicalVivaQuestionNumberChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label={`Passing Mark (${type.type})`}
                            type="number"
                            value={passingMarkTechnical}
                            onChange={handlePassingMarkTechnicalVivaChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" fullWidth onClick={handleSubmit}>
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            )}

            {showSuccessMessage && (
                <Alert variant="success">{successMessage}</Alert>
            )}
            {showErrorMessage && (
                <Alert variant="danger">{errorMessage}</Alert>
            )}
        </Paper>
    );
};

export default InputNumbersComponent;
