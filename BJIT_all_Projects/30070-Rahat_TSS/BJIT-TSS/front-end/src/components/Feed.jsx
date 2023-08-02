import React, { useContext } from 'react'
import { Box } from "@mui/material";

import { Route, Routes } from 'react-router-dom/dist/index';
import HomePage from '../pages/HomePage';
import CoursesPage from '../pages/CoursesPage';
import LoginPage from '../pages/LoginPage';
import RegistrationPage from '../pages/RegistrationPage';
import NotFound from './NotFound';
import { LoginContext } from '../context/LoginContex';
import UploadFilePage from '../pages/UploadFilePage';
import EmailVerification from '../pages/EmailVerification';
import ProfilePage from '../pages/ProfilePage';
import CourseDescriptionComponent from '../pages/CourseDetailsPage';
import NoticeBoardPage from '../pages/NoticeBoard';
import EvaluatorManagement from '../pages/EvaluatorManagement';
import ApproveApplicantPage from '../pages/ApproveApplicantPage';
import UploadWrittenMark from '../pages/UploadWrittenMark';
import FinalTraineesPage from '../pages/FinalTraineesPage';
import HrInterviewPage from '../pages/HrInterviewPage';
import TechnicalInterviewPage from '../pages/TechnicalInterviewPage';
import AptitudeTestPage from '../pages/AptitudeTestPage';
import WrittenTestPage from '../pages/WrittenTestPage';

const Feed = ({ data, loading, close }) => {

  const { role, uploaded, loggedIn } = useContext(LoginContext);


  return (
    <Box pl={ {xs : 0 , sm: 5}} pr={ {xs : 2 , sm: 0}}  flex={5} sx={{ margin: 5, display: { xs: close ? "block" : "none" } }}>


      <Routes>
        {(role === "APPLICANT" ? (
          <Route path="/" element={!loading ? <NoticeBoardPage /> : <h1>Loading</h1>} />
        ) :
          (role != "USER" &&
            <Route path="/" element={!loading ? <HomePage data={data} /> : <h1>Loading</h1>} />
          )
        )}

        {
          role == "USER" &&
          <Route path="/" element={
            role === "USER" ? (!uploaded ? <UploadFilePage /> : <EmailVerification />) : <RegistrationPage />
          } />

        }
        {role == "EVALUATOR" &&
          <Route path="/upload-written-marks" element={!loading ? <UploadWrittenMark /> : <h1>Loading</h1>} />

        }


        {(role !== "EVALUATOR" && role !== "USER") &&
          <Route path="/course">
            <Route index element={<CoursesPage courses={data} />} />
            <Route path=":id" element={<CourseDescriptionComponent />} />

          </Route>
        }


        {!loggedIn && <>

          <Route path="/login" element={<LoginPage />} />

        </>}

        <Route
          path="/registration"
          element={
            role === "USER" ? (!uploaded ? <UploadFilePage /> : <EmailVerification />) : <RegistrationPage />
          }
        />

        {
          (role === "APPLICANT" || role === "EVALUATOR") &&
          <Route path="/profile" element={<ProfilePage />} />

        }

        {
          role === "ADMIN" &&
          <>
            <Route path="/evaluator_management" element={<EvaluatorManagement />} />
            <Route path="/approve_applicant" element={<ApproveApplicantPage />} />
            <Route path="/aptitude_test" element={<AptitudeTestPage />} />
            <Route path="/technical_interview" element={<TechnicalInterviewPage />} />
            <Route path="/hr_interview" element={<HrInterviewPage />} />
            <Route path="/final_trainees" element={<FinalTraineesPage />} />
            <Route path="/written_test" element={<WrittenTestPage />} />


          </>
        }



        <Route path="*" element={<NotFound />} />


      </Routes>
    </Box>

  )
}

export default Feed