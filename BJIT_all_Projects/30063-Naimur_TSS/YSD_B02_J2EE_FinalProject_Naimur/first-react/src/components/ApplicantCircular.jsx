import React, { useState, useEffect } from "react";
import { Card, CardBody, CardSubtitle, CardText, Button, Container } from "reactstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import base_url from "../api/bootapi";
import jwt_decode from "jwt-decode";

const ApplicantCircular = ({ circular }) => {
  const [applicantId, setApplicantId] = useState(null);

  useEffect(() => {
    const fetchApplicantId = () => {
      try {
        const applicantIdFromStorage = localStorage.getItem("applicantId");
        setApplicantId(parseInt(applicantIdFromStorage, 10));
      } catch (error) {
        console.error("Failed to fetch applicantId:", error);
      }
    };

    fetchApplicantId();
  }, []);

  const handleApply = async () => {
    try {
      console.log("Applicant ID:", applicantId);
      const token = localStorage.getItem('token');
      const applyData = {
        circularId: circular.circularId,
        applicantId: applicantId,
      };
      await axios.post(`${base_url}/applicant/apply`, applyData, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });

      alert("Successfully applied for the circular!");
    } catch (error) {
      console.error("Failed to apply for the circular:", error);
    }
  };

  return (
    <Card className="text-center">
      <CardBody>
        <CardSubtitle className="font-weight-bold text-primary">
          {circular.circularId} : {circular.circularName}
        </CardSubtitle>
        <CardText>{circular.detail}</CardText>
        <CardText>Registration deadline: {circular.endDate}</CardText>
        <Container className="text-center">
          <Button color="primary" onClick={handleApply}>
            Apply
          </Button>
        </Container>
      </CardBody>
    </Card>
  );
};

export default ApplicantCircular;
