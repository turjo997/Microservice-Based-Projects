import React, { useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TableSortLabel, Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import axios from '../api/axios';

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

const ApplicantTable = ({ applicants, showAction, setApplicants, action, actionText }) => {
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
                                    active={sortField === 'lastName'}
                                    direction={sortField === 'lastName' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('lastName')}
                                >
                                    Last Name
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
                                    active={sortField === 'cgpa'}
                                    direction={sortField === 'cgpa' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('cgpa')}
                                >
                                    CGPA
                                </TableSortLabel>
                            </StyledTableCell>
                            <StyledTableCell>
                                <TableSortLabel
                                    active={sortField === 'presentAddress'}
                                    direction={sortField === 'presentAddress' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('presentAddress')}
                                >
                                    Present Address
                                </TableSortLabel>
                            </StyledTableCell>
                            <StyledTableCell>
                                <TableSortLabel
                                    active={sortField === 'degreeName'}
                                    direction={sortField === 'degreeName' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('degreeName')}
                                >
                                    Degree Name
                                </TableSortLabel>
                            </StyledTableCell>
                            <StyledTableCell>
                                <TableSortLabel
                                    active={sortField === 'passingYear'}
                                    direction={sortField === 'passingYear' ? sortOrder : 'asc'}
                                    onClick={() => handleSort('passingYear')}
                                >
                                    Passing Year
                                </TableSortLabel>
                            </StyledTableCell>
                            <StyledTableCell>
                                <TableSortLabel

                                >
                                </TableSortLabel>
                            </StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {sortedApplicants.map((applicant, index) => (
                            <TableRow key={index}>
                                <TableCell>{applicant.userInfo.firstName}</TableCell>
                                <TableCell>{applicant.userInfo.lastName}</TableCell>
                                <TableCell>{applicant.userInfo.email}</TableCell>
                                <TableCell>{applicant.userInfo.cgpa}</TableCell>
                                <TableCell>{applicant.userInfo.presentAddress}</TableCell>
                                <TableCell>{applicant.userInfo.degreeName}</TableCell>
                                <TableCell>{applicant.userInfo.passingYear}</TableCell>
                                <TableCell>
                                    {showAction &&
                                        <Button variant="contained" color="secondary" onClick={() => action(applicant.examineeId)}>
                                            {actionText}
                                        </Button>
                                    }
                                </TableCell>


                            </TableRow>
                        ))}
                    </TableBody>
                </ResponsiveTable>
            </ScrollableWrapper>
        </Container>
    );
};

export default ApplicantTable;
