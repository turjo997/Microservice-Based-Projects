import React from "react";
import { Link } from "react-router-dom";
import { ListGroup, ListGroupItem } from "reactstrap";

const EvaluatorMenus = () => {
    return (
        <ListGroup>
            <Link className="list-group-item list-group-item-action" tag="a" to="/" action>
                Home
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="/upload-marks" action>
                Upload Written Marks
            </Link>


            <Link className="list-group-item list-group-item-action" tag="a" to="#!" action>
                Logout
            </Link>
        </ListGroup>
        

    )
}

export default EvaluatorMenus;