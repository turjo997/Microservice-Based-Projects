import React, { useContext } from 'react'
import { Box } from "@mui/material";

import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { Home, School, AddBox, Login, AccountBox, Badge, Addchart, Assistant, BackupTable, HowToReg } from '@mui/icons-material';
import { NavLink, useLocation } from "react-router-dom";

import DomainVerificationIcon from '@mui/icons-material/DomainVerification';

import FactCheckIcon from '@mui/icons-material/FactCheck';
import Link from '@mui/material/Link';
import { Link as ReactRouterLink } from 'react-router-dom';
import { LoginContext } from '../context/LoginContex';
import { Logout } from '@mui/icons-material/index';


const Sidebar = ({ close }) => {
  const location = useLocation();



  const { setappliedCoursesGlobal, userData, setUserData, role, loggedIn, setLoggedIn, setRole } = useContext(LoginContext);



  // const [name, setName] = useState("User");

  // useEffect(() => {
  //   if (userData && userData.data && userData.data.data.role === "APPLICANT") {
  //     setName(userData.data.data.data.firstName);
  //   }
  // }, [userData]);



  const logout = () => {
    window.localStorage.removeItem("tss-token")

    setLoggedIn(false);
    setRole("");
    setappliedCoursesGlobal(0)
    setUserData(null);

  }


  const isActiveLink = (path) => {


    let extracted = location.pathname.split("/");
    let final = "/" + extracted[1];
    if (location.pathname === path || final === path) {
      return "#9c27b0";
    }



  };
  return (

    <Box flex={1} sx={{ color: "black", backgroundColor: "#EDE4FF", display: { xs: close ? "none" : "block", sm: "block" } }}>
      <Box position="fixed" >

        <List>
          {role !== "USER" &&
            < ListItem disablePadding>
          <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/") }} component={NavLink} to="/" variant="ListItemButton"   >
            <ListItemButton >
              <ListItemIcon>
                <Home />
              </ListItemIcon>
              <ListItemText primary={role === "APPLICANT" ? "Notice Board" : "Home Page"} />
            </ListItemButton>
          </Link>
          </ListItem>

}

        {(role === "APPLICANT" || role == "EVALUATOR") &&
          <ListItem disablePadding>
            <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/profile") }} component={NavLink} to="/profile" variant="ListItemButton"   >
              <ListItemButton >
                <ListItemIcon>
                  <Home />
                </ListItemIcon>
                <ListItemText primary="Profile" />
              </ListItemButton>
            </Link>
          </ListItem>
        }

        {role == "EVALUATOR" &&

          <ListItem disablePadding>
            <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/upload-written-marks") }} component={NavLink} to="/upload-written-marks" variant="ListItemButton"   >
              <ListItemButton >
                <ListItemIcon>
                  <Home />
                </ListItemIcon>
                <ListItemText primary="Upload Marks" />
              </ListItemButton>
            </Link>
          </ListItem>

        }



        {(role !== "EVALUATOR" && role !== "USER") &&

          <ListItem disablePadding>
            <Link flex={1.5} style={{ textDecoration: 'none', color: isActiveLink("/course") }} component={ReactRouterLink} to="/course" variant="ListItemButton" >
              <ListItemButton >
                <ListItemIcon>
                  <School />
                </ListItemIcon>
                <ListItemText primary="Courses" />
              </ListItemButton>
            </Link>
          </ListItem>
        }

        {role === "ADMIN" &&

          <>
            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/final_trainees") }} component={NavLink} to="/final_trainees" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    <AccountBox />
                  </ListItemIcon>
                  <ListItemText primary="Final Trainees" />
                </ListItemButton>
              </Link>
            </ListItem>
            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/hr_interview") }} component={NavLink} to="/hr_interview" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    < Badge />
                  </ListItemIcon>
                  <ListItemText primary="HR Interview" />
                </ListItemButton>
              </Link>
            </ListItem>
            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/technical_interview") }} component={NavLink} to="/technical_interview" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    <Addchart />
                  </ListItemIcon>
                  <ListItemText primary="Technical Interview" />
                </ListItemButton>
              </Link>
            </ListItem>
            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/aptitude_test") }} component={NavLink} to="/aptitude_test" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    <Assistant />
                  </ListItemIcon>
                  <ListItemText primary="Aptitude Test" />
                </ListItemButton>
              </Link>
            </ListItem>

            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/written_test") }} component={NavLink} to="/written_test" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    <BackupTable />
                  </ListItemIcon>
                  <ListItemText primary="Written Test" />
                </ListItemButton>
              </Link>
            </ListItem>


            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/approve_applicant") }} component={NavLink} to="/approve_applicant" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    <FactCheckIcon />
                  </ListItemIcon>
                  <ListItemText primary="Approve Appicant" />
                </ListItemButton>
              </Link>
            </ListItem>



            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/evaluator_management") }} component={NavLink} to="/evaluator_management" variant="ListItemButton"   >
                <ListItemButton >
                  <ListItemIcon>
                    <HowToReg />
                  </ListItemIcon>
                  <ListItemText primary="Evaluators" />
                </ListItemButton>
              </Link>
            </ListItem>
          </>
        }


        {!loggedIn ?
          <>

            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/login") }} component={ReactRouterLink} to="/login" variant="ListItemButton" >
                <ListItemButton >
                  <ListItemIcon>
                    <Login />
                  </ListItemIcon>
                  <ListItemText primary="Login" />
                </ListItemButton>
              </Link>
            </ListItem>

            <ListItem disablePadding>
              <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/registration") }} component={ReactRouterLink} to="/registration" variant="ListItemButton" >
                <ListItemButton >
                  <ListItemIcon>
                    <AddBox />
                  </ListItemIcon>
                  <ListItemText primary="Registration" />
                </ListItemButton>
              </Link>
            </ListItem>
          </>
          :

          <ListItem disablePadding onClick={() => { logout() }}>
            <Link flex={1} style={{ textDecoration: 'none' }} component={ReactRouterLink} to="/" variant="ListItemButton" >
              <ListItemButton >
                <ListItemIcon>
                  <Logout />
                </ListItemIcon>
                <ListItemText primary="Logout" />
              </ListItemButton>
            </Link>
          </ListItem>

        }

        {role === "USER" ?

          <ListItem disablePadding>
            <Link flex={1} style={{ textDecoration: 'none', color: isActiveLink("/registration") }} component={ReactRouterLink} to="/registration" variant="ListItemButton" >
              <ListItemButton >
                <ListItemIcon>
                  <AddBox />
                </ListItemIcon>
                <ListItemText primary="Registration" />
              </ListItemButton>
            </Link>
          </ListItem> : null}



      </List>
    </Box>
    </Box >



  )
}

export default Sidebar