import React from 'react'

import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Link } from 'react-router-dom/dist/index';

const HomePage = ({ data }) => {



  return (

    <Card sx={{ minWidth: 275, marginTop: 4 }}>
      <CardContent>
        <Typography align="center" sx={{ fontSize: 34 }} color="text.secondary" gutterBottom>
          Welcome to BJIT Academy
        </Typography>
        <Typography variant="h5" component="div">
          Boost your career 
        </Typography>
        <Typography sx={{ mb: 1.5 }} color="text.secondary">
          Be Limitless
        </Typography>
        <Typography variant="body2">
          {(data ? data?.data.data.dataLength + " Courses are available" : null)}

        </Typography>
      </CardContent>
      <CardActions>
        <Link to="/course" >
          <Typography color="primary"  variant="Button" size="small" >See Avaialable Courses</Typography>
        </Link>
      </CardActions>
    </Card>



  )
}

export default HomePage