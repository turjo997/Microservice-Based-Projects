import Feed from "./components/Feed";
import Navbar from "./components/Navbar";
import Rightbar from "./components/Rightbar";
import Sidebar from "./components/Sidebar";
import { Box, Stack } from "@mui/material";
import useFetch from "./hooks/useFetch";
import { useEffect, useState } from "react";
// import useLocalStorage from "./hooks/useLocalStorage";

import { LoginContext } from "./context/LoginContex";
import axios from "./api/axios";
import JSON2Message from "./services/JSON2Message";



function App() {
  const { data, loading, error } = useFetch("api/course");

  const [role, setRole] = useState("");
  const [courses, setCourses] = useState([]);
  const [allEvaluators, setAllEvaluators] = useState([]);
  const [unavailableCourses, setUnavailableCourses] = useState([]);

  const [appliedCoursesGlobal, setappliedCoursesGlobal] = useState(0)


  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("")

  useEffect(() => {
    if (role === 'ADMIN') {

      console.log("Admin logged in.");

      const token = window.localStorage.getItem("tss-token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };



      axios.get('/api/evaluator/get-all', config)
        .then((response) => {
          console.log(response?.data?.data?.listResponse);

          if (response.status === 200) {
            setShowSuccessMessage(true)
            setSuccessMessage(response.data.successMessage)
            setAllEvaluators(response?.data?.data?.listResponse)

            setTimeout(() => {
              setShowSuccessMessage(false)
              setSuccessMessage("")


            }, 2000);
          }
        })
        .catch((error) => {
          console.error('Error getting all evaluator: ', error);
          setShowErrorMessage(true)
          setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

          setTimeout(() => {
            setShowErrorMessage(false)
            setErrorMessage("")

          }, 1000);


        });



      axios.get('/api/course/unavailable', config)
        .then((response) => {
          console.log(response?.data?.data?.listResponse);

          if (response.status === 200) {
            setShowSuccessMessage(true)
            setSuccessMessage(response.data.successMessage)
            setUnavailableCourses(response?.data?.data?.listResponse)

            setTimeout(() => {
              setShowSuccessMessage(false)
              setSuccessMessage("")


            }, 2000);
          }
        })
        .catch((error) => {
          console.error('Error getting unavailavle courses', error);
          setShowErrorMessage(true)
          setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))

          setTimeout(() => {
            setShowErrorMessage(false)
            setErrorMessage("")

          }, 1000);


        });





    }


  }, [role])


  useEffect(() => {
    if (data?.data?.data) {
      setCourses(data?.data?.data?.listResponse)
      console.log(data?.data?.data?.listResponse);
    }

  }, [data])



  useEffect(() => {
    console.log(courses);
  }, [courses])




  const [loggedIn, setLoggedIn] = useState(false);

  const [userData, setUserData] = useState(null);
  const [errorss, setError] = useState(null);
  const [uploaded, setUploaded] = useState(false)




  useEffect(() => {
    console.log("The userData is :");
    console.log(userData);


  }, [userData])

  useEffect(() => {
    console.log(role);
  }, [role])




  useEffect(() => {
    const token = window.localStorage.getItem("tss-token")
    axios.get('/api/auth/validation', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then((response) => {



      console.log("Response is :");
      console.log(response.data.data.data);
      if (response.status == 200) {
        setUserData(response.data.data.data);

        setRole(response.data.data.role)
        setLoggedIn(true);

        if (response.data.data.role == "USER" && (response?.data?.data?.data?.photoUrl == null || response?.data?.data?.data?.resumeUrl == null)) {
          setUploaded(false)
        }

      }
    }).catch((err) => {
      setError(err);
      setLoggedIn(false);

    }).finally(() => {
      console.log("Loding Finished ");
    })

  }, [])



  useEffect(() => {
    if (userData) {
      // console.log(userData);

    }
    if (errorss) {
      console.log(errorss);

    }
  }, [userData])



  useEffect(() => {
    if (!loading && data) {
      // console.log(data);

    }
    if (!loading && error) {
      console.log(error);

    }
  }, [loading])

  useEffect(() => {
    console.log("Login Status :" + loggedIn);

  }, [loggedIn])




  const [close, setClose] = useState(true);

  const toggleSideBar = () => {
    setClose(prev => !prev)
  }
  return (

    <Box sx={{ backgroundColor: "#EDE4FF", minHeight: "100vh" }} >
      <LoginContext.Provider value={{ allEvaluators, setAllEvaluators, appliedCoursesGlobal, setappliedCoursesGlobal, courses, setUserData, userData, uploaded, setUploaded, loggedIn, setLoggedIn, role, setRole, setCourses, unavailableCourses, setUnavailableCourses }}>

        <Navbar courseNumber={data?.data.data.dataLength} onClose={toggleSideBar} />
        <Stack direction="row" spacing={2} justifyContent="space-between">
          <Sidebar close={close} />

          <Feed data={data} close={close} loading={loading} />
          <Rightbar />

        </Stack>
      </LoginContext.Provider>

    </Box >


  );
}

export default App;
