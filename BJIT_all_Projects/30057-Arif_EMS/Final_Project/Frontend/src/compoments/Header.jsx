import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = ({role}) => {
  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Navbar.Brand href="#home">E-M-S</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          <Nav.Link as={Link} to="/allBatch">Home</Nav.Link>
          <Nav.Link as={Link} to="/finalmark">FinalMarks</Nav.Link>
          { role=="ADMIN" ? (<> <Nav.Link as={Link} to="/createbatch">CreateBatch</Nav.Link> </>) : null }
          {(role=="TRAINER" || role=="ADMIN") ? (<> <Nav.Link as={Link} to="/createtask">CreateTask</Nav.Link> </>) : null }
          <Nav.Link as={Link} to="/">Login</Nav.Link>
          <Nav.Link as={Link} to="/logout">LogOut</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Header;

