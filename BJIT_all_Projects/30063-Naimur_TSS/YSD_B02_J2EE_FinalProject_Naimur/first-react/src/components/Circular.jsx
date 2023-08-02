import React from "react";

import{
    Card,
    CardBody,
    CardTitle,
    CardSubtitle,
    CardText,
    CardFooter,
    Button,
    Container,

} from "reactstrap";
import axios from "axios";
import base_url from "../api/bootapi";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";

const Circular=({circular,update})=>{
    const deleteCircular=(circularId)=>{
        const token = localStorage.getItem('token');
        axios.delete(`${base_url}/admin/circular/${circularId}`),{
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        .then((response)=>{
            toast.success("Circular deleted");
            update(circularId);
        },
        (error)=>{
            toast.error("Circular not deleted!! Server problem");
        }
        )
    }
    return (
        <Card className="text-center">
            <CardBody>
                <CardSubtitle className="font-weight-bold text-primary">{circular.circularId} : {circular.circularName}</CardSubtitle>
                <CardText>{circular.detail}</CardText>
                <CardText>Status: {circular.status}</CardText>
                <CardText>Registration deadline: {circular.endDate}</CardText>
                <Container className="text-center">
                    <Button color="danger" onClick={()=>{deleteCircular(circular.circularId)}}>Delete</Button>
                    <Link to={`/update-circular/${circular.circularId}`}>
                        <Button color="warning" style={{ marginLeft: "8px" }}>Update</Button>
                    </Link>

                    <Link to={`/admin/application/${circular.circularId}`}>
                    <Button color="primary" style={{ marginLeft: "8px" }}>View Application</Button>
                    </Link>

                </Container>
            </CardBody>
        </Card>
    );
    
}

export default Circular;