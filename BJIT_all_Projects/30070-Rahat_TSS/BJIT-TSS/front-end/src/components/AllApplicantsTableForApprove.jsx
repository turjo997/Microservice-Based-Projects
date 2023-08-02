import React, { useEffect, useState } from 'react';
import { Menu, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TableSortLabel, Button, Box } from '@mui/material';
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

const AllApplicantsTableForApprove = ({fetchApplicant, batchCode, allInstitute, fetchIntitutesList, goBack, applicants, showAction, setApplicants, action, actionText }) => {



    const [sortField, setSortField] = useState('');
    const [sortOrder, setSortOrder] = useState('asc');
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("")
    const handleSort = (field) => {
        if (field === sortField) {
            setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
        } else {
            setSortField(field);
            setSortOrder('asc'); // Set the default sort order to 'asc' when a new sorting field is selected
        }
    };

    const sortedApplicants = [...applicants].sort((a, b) => {
        if (!sortField) return 0; // If no sorting field is selected, maintain the original order

        const aValue = a.userInfo[sortField]; // Access the sorting field from the userInfo object
        const bValue = b.userInfo[sortField];

        if (typeof aValue === 'undefined' || typeof bValue === 'undefined') return 0; // Null check

        if (typeof aValue === 'string' && typeof bValue === 'string') {
            // Use localeCompare for string comparison
            if (sortOrder === 'asc') {
                return aValue.localeCompare(bValue);
            } else {
                return bValue.localeCompare(aValue);
            }
        } else {
            // Handle non-string values separately
            if (sortOrder === 'asc') {
                return aValue < bValue ? -1 : 1;
            } else {
                return bValue < aValue ? -1 : 1;
            }
        }
    });



    const actionTextView = "View Profile"
    const actionView = (a) => {
        console.log(a);
    }


    const [selectedApplicant, setSelectedApplicant] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const handleOpenModal = (applicant) => {
        setSelectedApplicant(applicant);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setSelectedApplicant(null);
        setShowModal(false);
    };



    const [anchorEl, setAnchorEl] = useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
        console.log("hi");
    };


    const searchInstitution = (name) => {

        setAnchorEl(null);
        if (name == "all") {

            fetchApplicant();
        } else {



            let role = "APPLICANT"

            const formData = {
                role,
                batchCode,
                educationalInstitution: name
            };
            console.log(formData);

            const token = window.localStorage.getItem("tss-token");
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            };

            axios.post(`/api/application/course/distinct`, formData, config)
                .then((response) => {
                    console.log(response);
                    console.log(response?.data?.data);


                    if (response.status === 200) {
                        setShowSuccessMessage(true)
                        setSuccessMessage(response.data.successMessage)
                        console.log(response?.data?.data?.listResponse);
                        setApplicants(response?.data?.data?.listResponse)
                    


                        setTimeout(() => {
                            setShowSuccessMessage(false)
                            setSuccessMessage("")



                        }, 2000);
                    }
                })
                .catch((error) => {
                    console.error('Error uploading files:', error);
                    setShowErrorMessage(true)
                    setErrorMessage(JSON2Message(JSON.stringify(error.response.data.errorMessage)))
                    setTimeout(() => {
                        setShowErrorMessage(false)
                        setErrorMessage("")

                    }, 5000);


                });

        }
    }

    useEffect(() => {
        fetchIntitutesList();
    }, [])

    return (
        <Box sx={{ maxWidth: "100" }}>
            <Button onClick={goBack}>Go Back</Button>

            <Container component={Paper}>

                <ScrollableWrapper>
                    <ResponsiveTable stickyHeader>
                        <TableHead>
                            <TableRow>
                                <StyledTableCell>
                                    <TableSortLabel
                                    >
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
                                    <Box>


                                        <Button sx={{ bgcolor: "#571fcb1a", color: "black" }} variant="contained" onClick={handleClick}>
                                            Educational Institute
                                        </Button>
                                        <Menu
                                            anchorEl={anchorEl}
                                            open={Boolean(anchorEl)}
                                            onClose={handleClose}
                                        >
                                            <MenuItem sx={{ flex: 1 }} onClick={() => { searchInstitution("all") }}>Show All</MenuItem>

                                            {allInstitute.map((value) => {

                                                return (
                                                    <MenuItem sx={{ flex: 1 }} onClick={() => { searchInstitution(value) }}>{value}</MenuItem>
                                                )
                                            })}

                                        </Menu>
                                    </Box>
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
                                    <TableCell>
                                        {showAction && (
                                            <Button variant="contained" color="primary" onClick={() => handleOpenModal(applicant)}>
                                                {actionTextView}
                                            </Button>
                                        )}
                                    </TableCell>
                                    <TableCell>{applicant.userInfo.firstName}</TableCell>
                                    <TableCell>{applicant.userInfo.educationalInstitute}</TableCell>
                                    <TableCell>{applicant.userInfo.cgpa}</TableCell>
                                    <TableCell>{applicant.userInfo.degreeName}</TableCell>
                                    <TableCell>{applicant.userInfo.passingYear}</TableCell>
                                    <TableCell>
                                        {showAction &&
                                            <Button variant="contained" color="secondary" onClick={() => action(applicant)}>
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



export default AllApplicantsTableForApprove