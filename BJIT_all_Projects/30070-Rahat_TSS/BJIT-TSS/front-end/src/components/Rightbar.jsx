import React from 'react'
import { Box, Container, Stack, Typography } from "@mui/material";

const Rightbar = () => {

  return (
    <Box flex={1} p={2} sx={{ display: { xs: "none", sm: "block" } }}  >
      <Box position="fixed">

        <Typography variant="h6" fontWeight={400}>Latest Updates</Typography>
        <Typography variant="p" fontWeight={300}>Last exam was held at 22th July.</Typography>

      </Box>
    </Box>
  )
}

export default Rightbar