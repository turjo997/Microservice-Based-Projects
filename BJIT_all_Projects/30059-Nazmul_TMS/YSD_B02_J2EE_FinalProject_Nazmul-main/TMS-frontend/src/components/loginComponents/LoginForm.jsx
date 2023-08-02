import React, { useState, useEffect } from 'react';
import { Container, Form, Button, Card } from 'react-bootstrap';
import './LoginForm.css'; // Import the custom CSS file for styling
import { useDispatch, useSelector } from 'react-redux';
import {login} from '../../redux/authRedux/authActions'
// import { NavLink } from 'react-router-dom';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const dispatch = useDispatch();
  const token = useSelector((state)=>state.auth.token);
  const error = useSelector((state) => state.auth.error);
  
  const handleSubmit = (e) => {
    e.preventDefault();
    // Perform login logic here
    dispatch(login(email, password)); 
  };

  useEffect(() => {
    if (token) {
      // Redirect to the home page when the user is logged in
      window.location.href = '/dashboard';
    }
  }, [token]);
  
  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card className="login-card">
        <Card.Body>
          <Card.Title className="text-center"><b>TMS</b> Login</Card.Title>
          <Form onSubmit={handleSubmit}>
            <div className="mb-3">
              <Form.Floating>
                <Form.Control
                  type="email"
                  placeholder="Enter email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
                <label htmlFor="floatingEmail">Email address</label>
              </Form.Floating>
            </div>

            <div className="mb-3">
              <Form.Floating>
                <Form.Control
                  type="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <label htmlFor="floatingPassword">Password</label>
              </Form.Floating>
            </div>

            <div className="text-center">
              
              <Button variant="primary" type="submit">
                Log in
              </Button>
              
            </div>
          </Form>
          {error && <p style={{color:'red', justifyContent:'center',marginLeft:'20px'}}>{error}</p>}

        </Card.Body>
      </Card>
    </Container>
  );
};

export default LoginForm;
