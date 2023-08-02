import React, { useState } from 'react';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TableSortLabel, Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import axios from '../api/axios';
import ApplicantModal from './ApplicantModal';

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

const FinalTraineeTable = ({ applicants, setApplicants, action, actionText }) => {
    console.log(applicants);
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


    const [selectedApplicant, setSelectedApplicant] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const handleOpenModal = (applicant) => {
        setSelectedApplicant(applicant.examineeInfo);
        setShowModal(true);
    };
    const handleCloseModal = () => {
        setSelectedApplicant(null);
        setShowModal(false);
    };




    return (
        <Box>

            <Container component={Paper}>
                <ScrollableWrapper>
                    <ResponsiveTable stickyHeader>
                        <TableHead>
                            <TableRow>
                                <StyledTableCell>
                                    <TableSortLabel  >
                                    </TableSortLabel>
                                </StyledTableCell>
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
                                        active={sortField === 'fullMark'}
                                        direction={sortField === 'fullMark' ? sortOrder : 'asc'}
                                        onClick={() => handleSort('fullMark')}
                                    >
                                        Full Mark
                                    </TableSortLabel>
                                </StyledTableCell>
                                <StyledTableCell>
                                    <TableSortLabel
                                        active={sortField === 'hrViva'}
                                        direction={sortField === 'hrViva' ? sortOrder : 'asc'}
                                        onClick={() => handleSort('hrViva')}
                                    >
                                        HR Viva Mark
                                    </TableSortLabel>
                                </StyledTableCell>
                                <StyledTableCell>
                                    <TableSortLabel
                                        active={sortField === 'technicalViva'}
                                        direction={sortField === 'technicalViva' ? sortOrder : 'asc'}
                                        onClick={() => handleSort('technicalViva')}
                                    >
                                        Technical Viva Mark
                                    </TableSortLabel>
                                </StyledTableCell>
                                <StyledTableCell>
                                    <TableSortLabel
                                        active={sortField === 'aptitudeMark'}
                                        direction={sortField === 'aptitudeMark' ? sortOrder : 'asc'}
                                        onClick={() => handleSort('aptitudeMark')}
                                    >
                                        Aptitude Test Mark
                                    </TableSortLabel>
                                </StyledTableCell>
                                <StyledTableCell>
                                    <TableSortLabel
                                        active={sortField === 'writtenMark'}
                                        direction={sortField === 'writtenMark' ? sortOrder : 'asc'}
                                        onClick={() => handleSort('writtenMark')}
                                    >
                                        Written Test Mark
                                    </TableSortLabel>
                                </StyledTableCell>

                                <StyledTableCell>
                                    <TableSortLabel >
                                    </TableSortLabel>
                                </StyledTableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {sortedApplicants.map((applicant, index) => (
                                <TableRow key={index}>
                                    <TableCell>
                                        <Button variant="contained" color="primary" onClick={() => handleOpenModal(applicant)}>
                                            View Profile
                                        </Button>
                                    </TableCell>
                                    <TableCell>{applicant.examineeInfo.userInfo.firstName}</TableCell>
                                    <TableCell>{applicant.fullMark}</TableCell>
                                    <TableCell>{applicant.hrViva.roundMark}</TableCell>
                                    <TableCell>{applicant.technicalViva.roundMark}</TableCell>
                                    <TableCell>{applicant.aptitudeTest.roundMark}</TableCell>
                                    <TableCell>{applicant.writtenMarks.writtenMark}</TableCell>

                                    <TableCell>
                                        <Button variant="contained" color="secondary" onClick={() => action(applicant)}>
                                            {actionText}
                                        </Button>
                                    </TableCell>


                                </TableRow>
                            ))}
                        </TableBody>
                    </ResponsiveTable>
                </ScrollableWrapper>
            </Container>
            {
                selectedApplicant && (
                    <ApplicantModal
                        applicant={selectedApplicant}
                        open={showModal}
                        onClose={handleCloseModal}
                    />
                )
            }
        </Box >
    );
};




export default FinalTraineeTable