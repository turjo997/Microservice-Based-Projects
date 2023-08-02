import './Sidebar.css';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome, faEnvelope, faComment, faLayerGroup, faBook, faClipboard, faChalkboard, faChevronDown, faUsers, faTimes, faBars, faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { Image, Navbar } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { getUserRole, logout } from '../redux/authRedux/authActions';

const Sidebar = ({ isSidebarOpen, toggleSidebar }) => {
  // const [showDropdown, setShowDropdown] = useState({});
  const dispatch = useDispatch();

  const [showDropdown, setShowDropdown] = useState(false);

  useEffect(()=>{
    dispatch(getUserRole());
  })
  const role = useSelector((state)=>state.auth.role);

  // const handleDropdownToggle = () => {
  //   setShowDropdown(!showDropdown);
  // };

  const handleLogout = (e) => {
    e.preventDefault();
    dispatch(logout());
    window.location.href = "/";

  }
  // Define navigation items based on user role
  const navigationItems = [
    {
      name: 'Users',
      icon: faUsers,
      link: '/users',
      allowedRoles: ['ADMIN'], // Only admins can see this item
    },
    {
      name: 'Home',
      icon: faHome,
      link: '/dashboard',
      allowedRoles: ['TRAINER','TRAINEE'], // Add the allowed roles for this item
    },
    
    {
      name: 'Batch',
      icon: faLayerGroup,
      link: '/batch',
      allowedRoles: ['ADMIN'],
      subItems: [ // Add the sub-items for the dropdown
        {
          name: 'Batch trainees',
          link: 'trainees',
          allowedRoles: ['ADMIN'],
        }, // Only admins can see this item
      ]
    },
    {
      name: 'Course',
      icon: faBook,
      link: '/course',
      allowedRoles: ['ADMIN'],
    },
    {
      name: 'Schedules',
      icon: faCalendarAlt,
      link: '/schedules',
      allowedRoles: ['ADMIN', 'TRAINER', 'TRAINEE'],
    },
    {
      name: 'Assignment',
      icon: faClipboard,
      link: '/assignments',
      allowedRoles: ['ADMIN','TRAINER','TRAINEE'],
    },
    {
      name: 'Classroom',
      icon: faChalkboard,
      link: '/classrooms',
      allowedRoles: ['ADMIN','TRAINER','TRAINEE'],
    },
    {
      name: 'Chat',
      icon: faComment,
      link: '/chatPage',
      allowedRoles: ['ADMIN', "TRAINER", "TRAINEE"],
    },
  ];

  const toggleDropdown = (index) => {
    setShowDropdown((prevState) => ({
      ...prevState,
      [index]: !prevState[index],
    }));
  };

  const filteredItems = navigationItems.filter(
    (item) => item.allowedRoles.includes(role)
  );

  return (
    <>
      <div className={`sidebar ${isSidebarOpen ? 'open' : ''}`}>
        <div className="sidebar-toggle" onClick={toggleSidebar}>
          <FontAwesomeIcon icon={isSidebarOpen ? faTimes : faBars} />
        </div>
        <nav className="sidebar-nav">
          <ul className={`sidebar-nav-list ${isSidebarOpen ? 'open' : ''}`}>
            {filteredItems.map((item, index) => (
              <li key={index} className={`dropdown ${item.subItems ? 'has-dropdown' : ''}`}>
                <div className= "dropdown-header">
                <Link to={item.link} className='sidebar-icon' style={{ color: 'white', outline: 'none', textDecoration: 'none' }}>
                  <FontAwesomeIcon icon={item.icon} />
                </Link>
                <Link to={item.link} style={{ textDecoration: 'none' }}>
                  <span className={isSidebarOpen ? 'item-name' : 'hidden'}>{item.name}</span>
                </Link>
                {item.subItems && item.subItems.length > 0 && (
                  <span
                  className={`dropdown-icon ${showDropdown[index] ? 'open' : ''} ${isSidebarOpen ? '' : 'hidden'}`}
                  onClick={() => toggleDropdown(index)}
                >
                  <FontAwesomeIcon icon={faChevronDown} />
                </span>
                )}
                </div>
                
                {item.subItems && item.subItems.length > 0 && showDropdown[index] && (
                   <ul className="dropdown-content">
                   {item.subItems.map((subItem, subIndex) => (
                     <li key={subIndex}>
                       <Link to={subItem.link} style={{ color: 'white', outline: 'none', textDecoration: 'none' }}>{subItem.name}</Link>
                     </li>
                   ))}
                 </ul>
                )}

              </li>
            ))}
          </ul>
        </nav>
      </div>
      {/* 
    {isSidebarOpen ? 'hidden' : } */}
      <Navbar bg="light" fixed='top' className="justify-content-end p-2 top-bar">
        {/* <Image src="" roundedCircle width="40" height="40" className="mr-2" />
        <span>User Email</span> */}
        <h2 style={{marginRight:'50px'}}>TMS</h2>
        <span><button onClick={handleLogout} style={{
          backgroundColor: 'green',
          color: 'white',
          paddingLeft: '10px 20px',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
          marginTop: "5px",
          marginLeft: '5px'
        }}>Logout</button></span>
        {/* <LogoutButton /> */}
      </Navbar>

    </>
  );
};

export default Sidebar;

