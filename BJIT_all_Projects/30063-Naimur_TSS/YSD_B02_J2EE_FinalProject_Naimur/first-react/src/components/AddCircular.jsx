import React, { Fragment, useEffect, useState } from "react";
import { Button, Container, Form, FormGroup, Input } from "reactstrap";
import axios from "axios";
import base_url from "../api/bootapi";
import { toast } from "react-toastify";

const AddCircular=()=>{
    useEffect(()=>{
        document.title="Add Circulars";
    },[]);

    const [circular,setCirculars]=useState({
        circularId: "",
        circularName: "",
        detail: "",
        endDate: "",
        status: "active", 
    });
const handleForm=(e)=>{
    console.log(circular);
    postDatatoServer(circular);
    e.preventDefault();
}



const postDatatoServer=(data)=>{
    const loginToken = localStorage.getItem('token');
    axios.post(`${base_url}/admin/circular/create`,data,
{
    headers: {
      Authorization: `Bearer ${loginToken}`,
      'Content-Type': 'application/json',
    },
  }
).then(
    (response)=>{
        //success
        console.log(response);
        console.log("success");
        toast.success("Circular added successfully");
        setCirculars({circularId: "",circularName: "",detail: "",endDate: "",status: "active"});

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
                    <Input type="text" placeholder="Enter Circular Id here" name="circularId" id="circularId" 
                    onChange={(e)=>
                    setCirculars({...circular,circularId:parseInt(e.target.value, 10)})
                    }/>
                </FormGroup>
                <FormGroup>
                    <label for ="circularName">Circular Name</label>
                    <Input type="text" placeholder="Enter circular name here" name="circularName" id="circularName"
                    onChange={(e)=>
                        setCirculars({...circular,circularName:e.target.value})
                        }
                    />
                </FormGroup>

                <FormGroup>
                    <label for ="detail">Circular Detail</label>
                    <Input type="textarea" placeholder="Enter description here" name="detail" id="detail" style={{height:150}}
                    onChange={(e)=>
                        setCirculars({...circular,detail:e.target.value})
                        }
                    />
                </FormGroup>
                <FormGroup>
                    <label htmlFor="endDate">End Date</label>
                    <Input type="date" name="endDate" id="endDate" value={circular.endDate} 
                        onChange={(e) =>
                            setCirculars({...circular,endDate: e.target.value, 
                            })
                        }
                    />
                </FormGroup>

                <FormGroup>
                    <label for ="status">Status</label>
                    <Input type="select" name="status" id="status" style={{ width: "150px" }}
                    onChange={(e)=>
                        setCirculars({...circular,status:e.target.value})
                        }>
                    <option value="active">Active</option>
                    <option value="expired">Expired</option>
                    </Input>
                </FormGroup>

                <Container className="text-center">
                    <Button type="submit" color="success">Add Circular</Button>
                    <Button type="reset" color="warning">CLear</Button>
                </Container>

                
            </Form>
        </Fragment>
    )
}

export default AddCircular;