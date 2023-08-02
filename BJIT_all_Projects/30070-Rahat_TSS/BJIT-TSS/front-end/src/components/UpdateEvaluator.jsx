import React, { useState } from 'react';
import { Box, TextField, Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import axios from '../api/axios';
import { useNavigate } from 'react-router-dom';
import { Alert } from 'react-bootstrap';


const FormBox = styled(Box)(({ theme }) => ({
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(2),
    maxWidth: '400px',
    margin: '0 auto',
}));

const UpdateEvaluatorForm = ({ setAllEvaluators, setValue }) => {

    const navigate = useNavigate();


    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("")

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = {
            name,
            email,
            password
        };
        // Perform the action to add the evaluator using the email, password, and name values.
        // For example, you can make an API call here to send the data to the server.
        // Make sure to implement validation and error handling as needed.
        console.log('Form submitted');
        console.log(formData);
        const token = window.localStorage.getItem("tss-token");
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        axios.post(`/api/auth/register/evaluator`, formData, config)
            .then((response) => {
                console.log(response);
                console.log(response?.data?.data);


                if (response.status === 201) {
                    setShowSuccessMessage(true)
                    setSuccessMessage(response.data.successMessage)
                    setAllEvaluators(prev => [...prev, response?.data?.data])

                    setTimeout(() => {
                        setShowSuccessMessage(false)
                        setSuccessMessage("")
                        setValue("all-evaluators");

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

        // Reset the form fields
        setEmail('');
        setPassword('');
        setName('');
    };

    return (
        <Box pt={7}>

            <form onSubmit={handleSubmit}>
                <FormBox>
                    <TextField
                        label="Email"
                        type="email"
                        value={email}
                        onChange={handleEmailChange}
                        required
                    />
                    <TextField
                        label="Password"
                        type="password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                    <TextField
                        label="Name"
                        type="text"
                        value={name}
                        onChange={handleNameChange}
                        required
                    />
                    <Button variant="contained" color="primary" type="submit">
                        Add Evaluator
                    </Button>
                    {showSuccessMessage &&
                        <Alert key="success" variant="success">
                            {successMessage}
                        </Alert>
                    }

                    {showErrorMessage &&
                        <Alert key="danger" variant="danger">
                            {errorMessage}
                        </Alert>
                    }
                </FormBox>
            </form>


        </Box>

    );
};

export default UpdateEvaluatorForm;
