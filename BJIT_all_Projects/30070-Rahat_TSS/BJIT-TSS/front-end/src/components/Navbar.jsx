import React, { useContext, useEffect, useState } from 'react'
import { theme } from "../theme/theme";
import { Menu, Tooltip, MenuItem, Badge, Box, InputBase, Container, Stack, AppBar, Toolbar, Typography, styled, colors } from "@mui/material";
import { Mail, Notifications } from "@mui/icons-material";
import { Avatar } from '@mui/material/index';
import { LoginContext } from '../context/LoginContex';
import { useNavigate } from 'react-router-dom/dist/index';
import AvatarImage from './AvatarImage';
const StyledToolbar = styled(Toolbar)({
  display: "flex",
  justifyContent: "space-between"
})

const Search = styled("div")(({ theme }) => ({
  backgroundColor: "white",
  padding: "0 10px",
  borderRadius: theme.shape.borderRadius,
  width: "40%"

}))
const Icons = styled(Box)(({ theme }) => ({
  display: "none",
  gap: "20px",
  alignItems: "center",
  [theme.breakpoints.up("sm")]: {
    display: "flex"
  }

}))

const UserBox = styled(Box)(({ theme }) => ({
  display: "flex",
  gap: "10px",
  alignItems: "center",
  [theme.breakpoints.up("sm")]: {
    display: "none"
  }
}))
const Navbar = ({ courseNumber, onClose }) => {

  const navigate = useNavigate();

  const { loggedIn, setLoggedIn, userData, role, appliedCoursesGlobal, setappliedCoursesGlobal } = useContext(LoginContext);

  const logout = () => {
    window.localStorage.removeItem("tss-token")

    setLoggedIn(false);
  }
  useEffect(() => {
    if (role === "ADMIN") {
      setName("ADMIN")
    }
  }, [])

  useEffect(() => {
    if (!loggedIn) {
      setName("User")
    }
    else {
      console.log(role);
      if (role === "ADMIN") {
        setName("ADMIN")
      } else if (role === "EVALUATOR") {
        console.log(userData?.name);
        setName(userData?.name)

      }
      else {
        setName(userData?.firstName)
      }
    }

  }, [loggedIn])

  const [open, setOpen] = useState(false);

  const [name, setName] = useState("User");

  useEffect(() => {
    if (userData && role === "APPLICANT") {
      setName(userData.firstName)
    }
    if (role === "ADMIN") {
      setName("ADMIN")

    }
    if (role === "EVALUATOR" && userData) {
      setName(userData?.name)

    }

  }, [userData]);


  return (
    <AppBar position="sticky" sx={{ bgcolor: "#2d2c72" }} >
      <StyledToolbar>
        <Typography variant="h6" sx={{ display: { xs: "none", sm: "block" } }}>Trainee Selection System</Typography>

        <Typography onClick={() => onClose()} variant="h6" sx={{ display: { xs: "block", sm: "none" } }} >TSS</Typography>
        <Search><InputBase placeholder='Search..' /></Search>
        <Icons>

          {(role != "ADMIN" && role != "EVALUATOR") &&
            <Tooltip title={
              loggedIn ?
                `You have applied ${appliedCoursesGlobal} courses`
                :
                "You are not logged in"
            }>


              <Badge badgeContent={appliedCoursesGlobal} color="error">
                <Mail />
              </Badge>
            </Tooltip>
          }
          <Tooltip title={`There are ${courseNumber ? courseNumber : 0} courses available`}>

            <Badge badgeContent={courseNumber ? courseNumber : 0} color="error">
              <Notifications />
            </Badge>
          </Tooltip>

          {role == "APPLICANT" ?
            <AvatarImage id={userData.userId} size={30} />
            :
            <Avatar
              sx={{ width: 30, height: 30 }}
              src="https://www.w3schools.com/w3images/avatar2.png"

            />
          }


        </Icons>
        <UserBox onClick={e => { navigate("/profile") }}>

          {role == "APPLICANT" ?
            <AvatarImage id={userData.userId} />
            :
            <Avatar
              sx={{ width: 30, height: 30 }}
              src="https://www.w3schools.com/w3images/avatar2.png"

            />
          }
          <Typography variant="span">
            {name}
          </Typography>
        </UserBox>
      </StyledToolbar>

      <Menu
        id="demo-positioned-menu"
        aria-labelledby="demo-positioned-button"
        open={false}
        onClose={e => { setOpen(false) }}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}

      >

      </Menu>
    </AppBar>


  )
}

export default Navbar