import React, { useState } from 'react';
import axios from 'axios';
import Login from '../Login';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from "react-toastify";

const Registration = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [gender, setGender] = useState('');
  const [dob, setDob] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [contactNumber, setContactNumber] = useState('');
  const [degreeName, setDegreeName] = useState('');
  const [educationalInstitute, setEducationalInstitute] = useState('');
  const [cgpa, setCgpa] = useState('');
  const [passingYear, setPassingYear] = useState('');
  const [address, setAddress] = useState('');

  const handleRegistration = async (e) => {
    e.preventDefault();
    try {

      const data = {
        firstName,
        lastName,
        gender,
        dob,
        email,
        password,
        contactNumber,
        degreeName,
        educationalInstitute,
        cgpa,
        passingYear,
        address,
      };

      const response = await axios.post('http://localhost:8084/applicant/register', data);
      console.log(response.data);
      alert("Registration successful!");
      setRedirectToLogin(true);
    } catch (error) {
      console.error('Registration failed:', error);
    }
  };

  const [redirectToLogin, setRedirectToLogin] = useState(false);

  if (redirectToLogin) {
    return <Login />;
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h1 className="mb-4"> Registration</h1>
          <form onSubmit={handleRegistration}>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="First Name"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Last Name"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Gender"
                value={gender}
                onChange={(e) => setGender(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Date of Birth"
                value={dob}
                onChange={(e) => setDob(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="email"
                className="form-control"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="password"
                className="form-control"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Contact Number"
                value={contactNumber}
                onChange={(e) => setContactNumber(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Degree Name"
                value={degreeName}
                onChange={(e) => setDegreeName(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Educational Institute"
                value={educationalInstitute}
                onChange={(e) => setEducationalInstitute(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="CGPA"
                value={cgpa}
                onChange={(e) => setCgpa(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Passing Year"
                value={passingYear}
                onChange={(e) => setPassingYear(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Address"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
              />
            </div>
            <button type="submit" className="btn btn-primary w-100 mt-3">
              Register
            </button>
          </form>
          <p className="mt-3 text-center">
            Already have an account?{' '}
            <a href="#" onClick={() => setRedirectToLogin(true)}>
              Log in
            </a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Registration;
