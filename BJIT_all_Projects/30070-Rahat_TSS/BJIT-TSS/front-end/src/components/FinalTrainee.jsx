import React, { useState } from 'react';
import { styled, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TableSortLabel, Button, Box } from '@mui/material';
import axios from '../api/axios';
import ApplicantModal from './ApplicantModal'; 




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

const FinalTrainee = ({ type, topMessage, applicants, action, actionText, showAction }) => {
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
                                        active={sortField === 'marks'}
                                        direction={sortField === 'marks' ? sortOrder : 'asc'}
                                        onClick={() => handleSort('marks')}
                                    >
                                        HR Viva Marks
                                    </TableSortLabel>
                                </StyledTableCell>

                                <StyledTableCell>
                                    <TableSortLabel   >
                                        Technical Viva Marks
                                    </TableSortLabel>
                                </StyledTableCell>
                                <StyledTableCell>
                                    <TableSortLabel   >
                                        Aptitude Test Marks
                                    </TableSortLabel>
                                </StyledTableCell>
                                <StyledTableCell>
                                    <TableSortLabel   >
                                        Written Test Marks
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
            {
                selectedApplicant && (
                    <ApplicantModal
                        applicant={selectedApplicant}
                        open={showModal}
                        onClose={handleCloseModal}
                    />
                )
            }
        </Box>
    );
};



export default FinalTrainee