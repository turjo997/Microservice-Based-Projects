import './App.css';
import React, { useEffect, useState } from 'react';
import jwt_decode from 'jwt-decode';
import { Route, Routes } from 'react-router-dom';
import Header from './components/header/Header';
import CommonNav from './components/navbar/CommonNav';
import AdminNav from './components/navbar/AdminNav';
import ApplicantNav from './components/navbar/ApplicantNav';
import EvaluatorNav from './components/navbar/EvaluatorNav';
import Home from './pages/home/Home';
import CreateCircular from './pages/circular/CreateCircular';
import CircularShow from './pages/circular/CircularShow';
import UploadMark from './pages/marks/UploadMark';
import CreateExam from './pages/exams/CreateExam';
import Result from './pages/result/Result';
import CreateMail from './pages/mail/CreateMail';
import ShowAndSendMail from './pages/mail/ShowAndSendMail';
import ShowAllApplicant from './pages/applicant/ShowAllApplicant';
import Approve from './pages/approve/Approve';
import RegistrationForm from './pages/registration/RegistrationForm';
import LoginForm from './pages/login/LoginForm';
import ApplicantRegister from './pages/applicant/ApplicantRegister';
import Apply from './pages/apply/Apply';
import NotificationProp from './pages/notification/NotificationProp2';
import ResourceUpload from './pages/resource/ResourceUpload';
import GetAdmitCard from './pages/admitcard/GetAdmitCard';
import UploadWrittenMark from './pages/marks/UploadWrittenMark';
import Footer from './components/footer/Footer';
import ShowWrittenMarks from './pages/marks/ShowWrittenMarks';
import NotificationP from './pages/notification/NotificationP';

const App = () => {
  const [userRole, setUserRole] = useState(null); 

  const decodeToken = () => {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken = jwt_decode(token);
      //console.log('Decoded Token:', decodedToken); 
      const role = decodedToken?.roles?.[0]?.authority; 
      console.log('User Role:', role); 
      setUserRole(role);
    }
  };

  useEffect(() => {
    decodeToken();
  }, []);

  if (userRole === null) {
    return (
      <div>
        <header>
          <Header />
          <CommonNav />
        </header>
        <main style={{ backgroundColor: '', padding: 80 }}>
          <Routes>
            <Route path="/login-form" element={<LoginForm />} />
            <Route path="/registration-form" element={<RegistrationForm />} />
            <Route path="/" element={<RegistrationForm />} />
          </Routes>
        </main>
        <footer><Footer /></footer>
      </div>
    );
  }

  

  return (
    <div>
      <header>
        <Header />
        <CommonNav />
        {userRole === 'ADMIN' && <AdminNav />}
        {userRole === 'APPLICANT' && <ApplicantNav />}
        {userRole === 'EVALUATOR' && <EvaluatorNav />}
      </header>

      <main style={{ backgroundColor: '', padding: 80, minHeight: '400px', minWidth: '300px' }}>

        <div>
        <Routes>
          {/* Authenticated Routes for ADMIN */}
          {userRole === 'ADMIN' && (
            <>
              <Route path="/" element={<Home />} />
              <Route path="/create-circular" element={<CreateCircular />} />
              <Route path="/circular-show" element={<CircularShow />} />
              <Route path="/upload-mark" element={<UploadMark />} />
              <Route path="/create-exam" element={<CreateExam />} />
              <Route path="/result" element={<Result />} />
              <Route path="/create-mail" element={<CreateMail />} />
              <Route path="/show-send-mail" element={<ShowAndSendMail />} />
              <Route path="/show-all-applicant" element={<ShowAllApplicant />} />
              <Route path="/approve" element={<Approve />} />
              <Route path="/written-mark" element={<ShowWrittenMarks />} />
            </>
          )}

          {/* Authenticated Routes for APPLICANT */}
          {userRole === 'APPLICANT' && (
            <>
              <Route path="/" element={<Home />} />
              <Route path="/applicant-register" element={<ApplicantRegister />} />
              <Route path="/apply" element={<Apply />} />
              <Route path="/notification" element={<NotificationP />} />
              {/* <Route path="/resource-upload" element={<ResourceUpload applicantId={6} />} /> */}
              <Route path="/admitcard" element={<GetAdmitCard />} />
            </>
          )}

          {/* Authenticated Routes for EVALUATOR */}
          {userRole === 'EVALUATOR' && (
            <>
              <Route path="/written-mark-upoad" element={<UploadWrittenMark />} />
            </>
          )}

          {/* Fallback Route for unmatched URLs */}
          <Route path="*" element={<Home />} />
        </Routes>
        </div>
        
      </main>

      <footer>
        <Footer />
      </footer>
    </div>
  );
};

export default App;
