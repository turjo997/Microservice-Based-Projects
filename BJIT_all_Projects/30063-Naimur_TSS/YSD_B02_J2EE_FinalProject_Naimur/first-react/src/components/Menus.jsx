import React from "react";
import { Link } from "react-router-dom";
import { ListGroup, ListGroupItem } from "reactstrap";

const Menus = () => {
    return (
        <ListGroup>
            <Link className="list-group-item list-group-item-action" tag="a" to="/" action>
                Home
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="/add-circular" action>
                Add Circular
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="/view-circulars" action>
                View Circular
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="/candidate" action>
                Selected Candidate
            </Link>
            <Link className="list-group-item list-group-item-action" tag="a" to="/admin/showEvaluator" action>
                Evaluator
            </Link>
            
            <Link className="list-group-item list-group-item-action" tag="a" to="/admin/finalSelection" action>
                Final Selection
            </Link>

            <Link className="list-group-item list-group-item-action" tag="a" to="/admin/logOut" action>
                Log Out
            </Link>
        </ListGroup>
        

    )
}

export default Menus;