import React, { useEffect } from "react";
import {Container,Button} from "reactstrap";

const Home=()=>{
    useEffect(()=>{
        document.title="Home";
    },[]);
    return(
    <div className="bg-custom-color text-light py-5 text-center">
      <Container>
        <h1>BJIT Academy</h1>
        <p className="lead">Welcome to BJIT</p>

        <Button color="warning" outline>Press Enter to Explore</Button>
      </Container>
    </div>

    )
}

export default Home;