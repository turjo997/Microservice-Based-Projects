import React, { useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import { Paper, TextField, Typography, Button, Grid } from '@mui/material';
import { Alert } from 'react-bootstrap';
import axios from '../api/axios';
import JSON2Message from '../services/JSON2Message';

const MarksContainer = styled(Paper)(({ theme }) => ({
    padding: theme.spacing(3),
    maxWidth: 600,
    margin: 'auto',
}));

const UploadTechnicalMark = (candidate) => {

    
  


    const [hiddenCode, setHiddenCode] = useState('');
    const [marks, setMarks] = useState(['']);

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





    const handleMarkChange = (index, value) => {
        const updatedMarks = [...marks];
        updatedMarks[index] = value;
        setMarks(updatedMarks);
    };

    const handleAddMarkField = () => {
        setMarks([...marks, '']);
    };

    const handleRemoveMarkField = (index) => {
        const updatedMarks = [...marks];
        updatedMarks.splice(index, 1);
        setMarks(updatedMarks);
    };

    const handleSubmit = () => {
        const formattedMarks = marks.map(parseFloat);

        const formData = {
            candidateId: candidate.candidate.candidateId,
            marks: formattedMarks,
        };
        console.log(formData);

        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        axios.post("/api/evaluation/upload-mark/technical", formData, config)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    console.log(response?.data?.data);
                }
            }).catch((error) => {
                console.error('Error uploading technical marks:', error);
                setShowErrorMessage(true)
                setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

            });


    };

    return (
        <MarksContainer>
            <Typography variant="h6" gutterBottom>
            {`Upload technical mark of ${candidate?.candidate.examineeInfo?.userInfo?.firstName}`}
            </Typography>
         
            <Typography variant="h6" gutterBottom sx={{ marginTop: 2 }}>
                Marks:
            </Typography>
            {marks.map((mark, index) => (
                <Grid container spacing={2} alignItems="center" key={index}>
                    <Grid item xs={8}>
                        <TextField
                            fullWidth
                            variant="outlined"
                            type="number"
                            value={mark}
                            onChange={(e) => handleMarkChange(index, e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={2}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleAddMarkField}
                        >
                            +
                        </Button>
                    </Grid>
                    {index > 0 && (
                        <Grid item xs={2}>
                            <Button
                                variant="contained"
                                color="secondary"
                                onClick={() => handleRemoveMarkField(index)}
                            >
                                -
                            </Button>
                        </Grid>
                    )}
                </Grid>
            ))}
            <Button
                variant="contained"
                color="primary"
                fullWidth
                sx={{ marginTop: 2 }}
                onClick={handleSubmit}
            >
                Submit
            </Button>


            {showSuccessMessage && (
                <Alert variant="success">{successMessage}</Alert>
            )}
            {showErrorMessage && (
                <Alert variant="danger">{errorMessage}</Alert>
            )}


        </MarksContainer>
    );
};





export default UploadTechnicalMark