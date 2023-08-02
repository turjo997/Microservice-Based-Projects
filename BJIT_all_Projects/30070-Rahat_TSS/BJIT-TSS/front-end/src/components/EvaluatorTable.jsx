import React, { useState } from 'react';
import {Typography, Box,  Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TableSortLabel } from '@mui/material';
import { styled } from '@mui/material/styles';

const HeaderTypography = styled(Typography)(({ theme }) => ({
  fontSize: '1.6rem',
  marginBottom: theme.spacing(2),
}));

const Container = styled(TableContainer)(({ theme }) => ({
  maxHeight: 440,
}));
const StyledTableRow = styled(TableRow)(({ theme }) => ({
  cursor: 'pointer',
  '&:hover': {
    background: theme.palette.action.hover,
  },
}));

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  fontWeight: 'bold',

}));

const EvaluatorTable = ({topMessage, data, onRowClick }) => {
  const [sortField, setSortField] = useState('');
  const [sortOrder, setSortOrder] = useState('asc');

  const handleSort = (field) => {
    if (field === sortField) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortOrder('asc');
    }
  };

  const sortedData = sortField ? [...data].sort((a, b) => {
    const aValue = a[sortField];
    const bValue = b[sortField];
    if (sortOrder === 'asc') {
      return aValue.localeCompare(bValue);
    } else {
      return bValue.localeCompare(aValue);
    }
  }) : data;

  return (
    <Container component={Paper}>

      <Box p={2}>
        <HeaderTypography>

          {topMessage}
        </HeaderTypography>
      </Box>

      <Table stickyHeader>
        <TableHead>
          <TableRow>
            <StyledTableCell>
              <TableSortLabel
                active={sortField === 'name'}
                direction={sortField === 'name' ? sortOrder : 'asc'}
                onClick={() => handleSort('name')}
              >
                Evaluator Name
              </TableSortLabel>
            </StyledTableCell>
            <StyledTableCell>
              <TableSortLabel
                active={sortField === 'email'}
                direction={sortField === 'email' ? sortOrder : 'asc'}
                onClick={() => handleSort('email')}
              >
                Email
              </TableSortLabel>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {sortedData.map((evaluator, index) => (

            <StyledTableRow key={index} onClick={() => onRowClick(evaluator)}>
              <TableCell>{evaluator.name}</TableCell>
              <TableCell>{evaluator.email}</TableCell>
            </StyledTableRow>

          ))}
        </TableBody>
      </Table>
    </Container>
  );
};

export default EvaluatorTable;
