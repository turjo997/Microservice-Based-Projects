import React from "react";
import { Link } from "react-router-dom";
import { ListGroup, ListGroupItem } from "reactstrap";

const ApplicantMenus = () => {
    return (
        <ListGroup>
            <Link className="list-group-item list-group-item-action" tag="a" to="/" action>
                Home
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="/view-circulars" action>
                View Circular
            </Link>

            <Link className="list-group-item list-group-item-action" tag="a" to="/view-myapplications" action>
                My Applications
            </Link>

            <Link className="list-group-item list-group-item-action" tag="a" to="#!" action>
                Profile
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="#!" action>
                Logout
            </Link>
        </ListGroup>
        

    )
}

export default ApplicantMenus;