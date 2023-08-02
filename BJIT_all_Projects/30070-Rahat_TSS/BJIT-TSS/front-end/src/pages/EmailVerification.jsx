import React, { useContext, useState } from 'react';
import { Button, TextField, Box } from '@mui/material';
import axios from '../api/axios';
import { Alert } from 'react-bootstrap';
import { LoginContext } from '../context/LoginContex';
import { useNavigate } from 'react-router-dom/dist/index';

const EmailVerificationComponent = () => {
  const [isCodeSent, setIsCodeSent] = useState(false);
  const [verificationCode, setVerificationCode] = useState('');
  
  const {setRole, setUserData, setLoggedIn} = useContext(LoginContext);
  const navigate = useNavigate();

  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("")



  const handleSendEmail = () => {
    const token = window.localStorage.getItem("tss-token");
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    axios.get('api/auth/register/send-email-verification', config)
      .then((response) => {
        console.log(response.data.successMessage);

        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)

          setTimeout(() => {
            setShowSuccessMessage(false)
            setSuccessMessage("")
  
            
          }, 2000);
          
        }



      })
      .catch((error) => {
        console.error('Error uploading files:', error.response.data);
          setShowErrorMessage(true)
          setErrorMessage(error.data.errorMessage)

          setTimeout(() => {
            setShowErrorMessage(false)
            setErrorMessage("")
            
          }, 2000);


      });

    setIsCodeSent(true);
  };

  const handleSubmit = () => {
    console.log('Verification code submitted:', verificationCode);

    const data = {
      validationCode: verificationCode,
    };

    const token = window.localStorage.getItem("tss-token");

    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    axios.post('/api/auth/register/applicant/validation', data, config)
      .then((response) => {
        console.log(response.data);
        if (response.status === 200) {
          setShowSuccessMessage(true)
          setSuccessMessage(response.data.successMessage)

          setTimeout(() => {
            setShowSuccessMessage(false)
            setSuccessMessage("")
            window.localStorage.removeItem("uploadedfortss")  

            const token = window.localStorage.getItem("tss-token")
            axios.get('/api/auth/validation', {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }).then((response) => {
              setUserData(response);
              if (response.status==200) {
        
                setRole(response.data.data.role)
                setLoggedIn(true);
                window.location.reload(false);

                
              }
            }).catch((err) => {
              console.log(err);
        
            }).finally(() => {
              console.log("Loding Finished ");
            })

            navigate("/");

            
          }, 2000);
          
        }

      })
      .catch((error) => {
        console.error('Error submitting verification code:', error.response.data.errorMessage);
        setShowErrorMessage(true)
          setErrorMessage(error.response.data.errorMessage)

          setTimeout(() => {
            setShowErrorMessage(false)
            setErrorMessage("")
            
          }, 2000);


      });




  };

  return (
    <Box display="flex" flexDirection="column" alignItems="center" mt={7}>
      <Button
        variant="contained"
        color="primary"
        onClick={handleSendEmail}
        sx={{ width: '250px', height: '70px', fontSize: '18px', marginBottom: '16px', padding: "10px" }}
      >
        Send Email Verification
      </Button>


      {showSuccessMessage && (
        <Alert variant="success">{successMessage}</Alert>
      )}
      {showErrorMessage && (
        <Alert variant="danger">{errorMessage}</Alert>
      )}



      {isCodeSent && (
        <>
          <Box mt={2}>
            <TextField
              label="Verification Code"
              variant="outlined"
              value={verificationCode}
              onChange={(e) => setVerificationCode(e.target.value)}
              sx={{ width: '250px' }}
            />
          </Box>

          <Box mt={1}>
            <Button
              variant="contained"
              color="primary"
              onClick={handleSubmit}
              sx={{ width: '250px', height: '50px', fontSize: '18px' }}
            >
              Submit Code
            </Button>
          </Box>
        </>
      )}
    </Box>
  );
};

export default EmailVerificationComponent;
