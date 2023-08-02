import React, { useState } from 'react';
import { styled, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TableSortLabel, Button, Box } from '@mui/material';
import axios from '../api/axios';



const HeaderTypography = styled(Typography)(({ theme }) => ({
    fontSize: '1.6rem',
    marginBottom: theme.spacing(2),
}));

const Container = styled(TableContainer)(({ theme }) => ({
    maxHeight: 440,
}));

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    fontWeight: 'bold',
}));

const ResponsiveTable = styled(Table)(({ theme }) => ({
    minWidth: 700,
}));

const ScrollableWrapper = styled('div')(({ theme }) => ({
    overflowX: 'auto',
}));

const AptitudePassed = ({ type, topMessage, applicants, action, actionText, showAction }) => {
    const [sortField, setSortField] = useState('');
    const [sortOrder, setSortOrder] = useState('asc');

    console.log(applicants);

    const handleSort = (field) => {
        if (field === sortField) {
            setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
        } else {
            setSortField(field);
            setSortOrder('asc');
        }
    };


    const sortedApplicants = sortField
        ? [...applicants].sort((a, b) => {
            const aValue = a[sortField];
            const bValue = b[sortField];
            if (sortOrder === 'asc') {
                return aValue < bValue ? -1 : 1;
            } else {
                return bValue < aValue ? -1 : 1;
            }
        })
        : applicants;

    return (
        <Container component={Paper}>
            <Box p={2}>
                <HeaderTypography>

                    {topMessage}
                </HeaderTypography>
            </Box>
            <ScrollableWrapper>
                <ResponsiveTable stickyHeader>
                    <TableHead>
                        <TableRow>
                            <StyledTableCell>
                                <TableSortLabel
                                    active={sortField === 'firstName'}
                                    direction={sortField === 'firstName' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('firstName')}
                                >
                                    First Name
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
                            <StyledTableCell>
                                <TableSortLabel
                                    active={sortField === 'fullMark'}
                                    direction={sortField === 'fullMark' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('fullMark')}
                                >
                                    Full Mark
                                </TableSortLabel>
                            </StyledTableCell>
                            <StyledTableCell>
                                <TableSortLabel
                                    active={sortField === 'marks'}
                                    direction={sortField === 'marks' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('marks')}
                                >
                                    Individula Marks
                                </TableSortLabel>
                            </StyledTableCell>
                            {
                                showAction &&
                                <StyledTableCell>

                                    <TableSortLabel  >

                                    </TableSortLabel>
                                </StyledTableCell>
                            }
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {sortedApplicants.map((applicant, index) => (
                            <TableRow key={index}>



                                <TableCell>{applicant.examineeInfo.userInfo.firstName}</TableCell>
                                <TableCell>{applicant.examineeInfo.userInfo.email}</TableCell>
                                <TableCell>{applicant.aptitudeTest.roundMark}</TableCell>
                                <TableCell>{applicant.aptitudeTest.questionMarksList.map((item) => item.questionMark).join(', ')}</TableCell>



                                {
                                    showAction &&
                                    <TableCell>
                                        <Button variant="contained" color="secondary" onClick={() => action(applicant)}>
                                            {actionText}
                                        </Button>
                                    </TableCell>

                                }


                            </TableRow>
                        ))}
                    </TableBody>
                </ResponsiveTable>
            </ScrollableWrapper>
        </Container>
    );
};





export default AptitudePassed