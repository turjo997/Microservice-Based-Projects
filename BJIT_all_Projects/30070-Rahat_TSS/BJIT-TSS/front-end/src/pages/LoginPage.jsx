import React, { useContext, useEffect, useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
// import usePost from '../hooks/usePost';
import axios from '../api/axios';
import Alert from 'react-bootstrap/Alert';
import { useNavigate } from 'react-router-dom/dist/index';
import { LoginContext } from '../context/LoginContex';
import JSON2Message from '../services/JSON2Message'


const LoginForm = () => {
  const navigate = useNavigate();


  const { loggedIn, setLoggedIn , setRole, setUserData } = useContext(LoginContext);

  if (loggedIn) {
    console.log("Your are logged in redirecting to home ");
    navigate("/");

  }


  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  const [errorMessage, setErrorMessage] = useState("");
  const [loading, setLoading] = useState(false)
  const [successMessage, setSuccessMessage] = useState(null);

  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  // const isLogedIn=()=>{
  // }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // console.log(formData);


    setLoading(true);

    axios.post("/api/auth/login", formData,).then((response) => {
      setData(response);
    }).catch((err) => {
      setError(err);
    }).finally(() => {

      setLoading(false);
      // console.log("Posting Finished ");

    })
  };

  useEffect(() => {
    if (data) {
      // console.log(data);
      if (data.status === 200) {
        setSuccessMessage(data.data.successMessage)

        console.log(data.data);

        window.localStorage.setItem("tss-token", data.data.data.token)

        setTimeout(() => {
          setSuccessMessage(null);
          setLoggedIn(true)
          console.log(data.data.data.role);
          setRole(data.data.data.role)

          if (data?.data?.data?.role=="APPLICANT") {
            setUserData(data.data.data.userInfo);
          }
          if (data?.data?.data?.role=="EVALUATOR") {
            setUserData(data.data.data.evaluatorInfo);
          }
          navigate("/");

        }, 1000);
      }
      else {
        console.log(data);
      }
    }
    if (error) {
      setErrorMessage(JSON2Message(JSON.stringify(error?.response?.data?.errorMessage))
      );
      setTimeout(() => {
        setErrorMessage("");

      }, 2000);

    }

  }, [loading])






  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col lg={12}>
          <Form onSubmit={handleSubmit}>
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

            <Form.Group controlId="email">
              <Form.Label>Email Address</Form.Label>

              <Form.Control
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter email"
              />
            </Form.Group>

            <Form.Group controlId="password">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Password"
              />
            </Form.Group>

            <Button className="mt-3 p-2" variant="primary" type="submit" block="true">
              Login
            </Button>


          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginForm;
