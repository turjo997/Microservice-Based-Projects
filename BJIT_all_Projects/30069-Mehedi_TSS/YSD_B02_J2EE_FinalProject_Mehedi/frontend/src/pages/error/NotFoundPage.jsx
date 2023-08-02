import React from "react";
import { Result, Button } from "antd";
import { Link } from "react-router-dom";

function NotFoundPage() {
  return (
    <Result
      status="404"
      title="404 - Not Found"
      subTitle="Sorry, the page you visited does not exist."
      extra={
        <Link to="/">
          <Button type="primary">Go Home</Button>
        </Link>
      }
    />
  );
}

export default NotFoundPage;
