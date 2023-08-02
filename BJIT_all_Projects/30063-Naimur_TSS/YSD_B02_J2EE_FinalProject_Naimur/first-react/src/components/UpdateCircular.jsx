import React, { Fragment, useEffect, useState } from "react";
import { Button, Container, Form, FormGroup, Input } from "reactstrap";
import axios from "axios";
import base_url from "../api/bootapi";
import { toast } from "react-toastify";
import { useParams } from "react-router-dom";

const getToken = () => {
    const token = localStorage.getItem("token");
    return token;
  };

const UpdateCircular=()=>{
    const {circularId}=useParams();
    const [circular, setCirculars] = useState({
        circularId: circularId,
        circularName: "",
        detail: "",
        endDate: "",
        status: ""
    });
    
    useEffect(()=>{
        axios.get(`${base_url}/admin/circular/`+circularId, {
            headers: {
              Authorization: `Bearer ${getToken()}`,
            },
          })
        .then(res=>{setCirculars({...circular,circularId:res.data.circularId,circularName:res.data.circularName,detail:res.data.detail,endDate:res.data.endDate,status:res.data.status})})
        .catch(err=>console.log(err))
        document.title="Update Circulars";
    },[]);

const handleForm=(e)=>{
    console.log(circular);
    postDatatoServer(circular);
    e.preventDefault();
}



const postDatatoServer=(data)=>{axios.put(`${base_url}/admin/circular/update`,data, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  }).then(
    (response)=>{
        //success
        console.log(response);
        console.log("success");
        toast.success("Circular updated successfully");
        setCirculars({circularId: "",circularName: "",detail: "",endDate: "",status: ""});

    },
    (error)=>{
        //for error
        console.log(error);
        console.log("error");
        toast.success("Something went wrong");
    }
    );
};

    return(
        <Fragment>

            <h1 className="text-center my-3">Fill Circular Detail</h1>
            <Form onSubmit={handleForm}>
                <FormGroup>
                    <label for ="circularId">Circular Id</label>
                    <Input type="text" placeholder="Enter Circular Id here" name="circularId" id="circularId" value={circular.circularId} disabled
                    onChange={(e)=>
                    setCirculars({...circular,circularId:parseInt(e.target.value, 10)})
                    }/>
                </FormGroup>
                <FormGroup>
                    <label for ="circularName">Circular Name</label>
                    <Input type="text" placeholder="Enter circular name here" name="circularName" id="circularName" value={circular.circularName}
                    onChange={(e)=>
                        setCirculars({...circular,circularName:e.target.value})
                        }
                    />
                </FormGroup>

                <FormGroup>
                    <label for ="detail">Circular Detail</label>
                    <Input type="textarea" placeholder="Enter Detail here" name="detail" id="detail" style={{height:150}} value={circular.detail}
                    onChange={(e)=>
                        setCirculars({...circular,detail:e.target.value})
                        }
                    />
                </FormGroup>
                <FormGroup>
                <label for="endDate">End Date</label>
                <Input type="date" name="endDate" id="endDate" value={circular.endDate}
                    onChange={(e) =>
                        setCirculars({
                            ...circular,
                            endDate: e.target.value,
                        })
                    }
                />
                </FormGroup>

                <FormGroup>
                    <label for ="status">Status</label>
                    <Input type="select" name="status" id="status" style={{ width: "150px" }} value={circular.status}
                    onChange={(e)=>
                        setCirculars({...circular,status:e.target.value})
                        }>
                    <option value="active">Active</option>
                    <option value="expired">Expired</option>
                    </Input>
                </FormGroup>

                <Container className="text-center">
                    <Button type="submit" color="success">Update Circular</Button>
                    <Button type="reset" color="warning">CLear</Button>
                </Container>

                
            </Form>
        </Fragment>
    )
}

export default UpdateCircular;