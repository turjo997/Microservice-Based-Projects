import React, { useEffect, useState } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Modal from '@mui/material/Modal';
import MenuItem from '@mui/material/MenuItem';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Marks = () => {
  const [data, setData] = useState([]);
  const [open, setOpen] = useState(false);
  const [applicationId, setApplicationId] = useState('');
  const [marks, setMarks] = useState('');
  const [comment, setComment] = useState('');
  const [remark, setRemark] = useState('');
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');

  useEffect(() => {
    fetch('http://localhost:8082/marks/all', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
    })
      .then((response) => response.json())
      .then((jsonData) => {
        const dataWithIds = jsonData.map((row, index) => ({ ...row, id: index + 1 }));
        setData(dataWithIds);
      })
      .catch((error) => console.error('Error fetching data:', error));

    fetch('http://localhost:8082/marks-category', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
    })
      .then((response) => response.json())
      .then((jsonData) => {
        setCategories(jsonData);
        if (jsonData.length > 0) {
          setSelectedCategory(jsonData[0].categoryId);
        }
      })
      .catch((error) => console.error('Error fetching categories:', error));
  }, []);

  const handleAddMark = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    const payload = {
      applicationId: parseInt(applicationId),
      marksCategoryId: selectedCategory,
      marks: parseFloat(marks),
      comment,
      remark,
    };
  
    fetch('http://localhost:8082/marks', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify(payload),
    })
    .then((response) => {
      if (!response.ok) {
        // Handle server errors (e.g., 5xx)
        console.error('Server error:', response.statusText);
        throw new Error('Failed to submit data');
      }
      if (response.status === 201) {
        const location = response.headers.get('Location');
        console.log('Newly created resource URL:', location);
        // Optionally, you can navigate the user to the newly created resource URL
        // window.location.href = location;
  
        // Show success toast with a custom message
        toast.success('Mark added successfully!', {
          position: 'top-right',
          autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
        });
      } else {
        return response.json(); // Parse the response as JSON for other status codes
      } // Parse the response as JSON
    })
    .then((data) => {
      // If the response is a JSON object, data will be an object
      console.log('POST response data:', data);
      handleClose();
  
      // Show success toast with the success message from the backend

  
      // Update the data state with the newly added mark
      setData((prevData) => [...prevData, data]);
  
      // You may want to update the DataGrid with the newly created mark here
    })
    .catch((error) => {
      console.error('Failed to submit data:', error);
  
      // Show error toast with the error message from the backend
      toast.error('Failed to submit data. Please try again.', {
        position: 'top-right',
        autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
      });
    });
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    {
      field: 'applications.applicationId',
      headerName: 'Application ID',
      width: 150,
      valueGetter: (params) => params.row.applications.applicationId,
    },
    {
      field: 'applications.circularName',
      headerName: 'Circular Name',
      width: 200,
      valueGetter: (params) => params.row.applications.circularName,
    },
    {
      field: 'marksCategory.categoryName',
      headerName: 'Category Name',
      width: 200,
      valueGetter: (params) => params.row.marksCategory.categoryName,
    },
    { field: 'marks', headerName: 'Marks', width: 120 },
    { field: 'comment', headerName: 'Comment', width: 200 },
    { field: 'remark', headerName: 'Remark', width: 200 },
  ];

  return (
    <div style={{ width: '100%', maxWidth: '1000px', margin: '0 auto', marginTop: '10px' }}>
      <Button variant="contained" color="primary" onClick={handleAddMark}>
        Add Mark
      </Button>
      <div style={{ height: '500px', width: '100%', overflow: 'auto' }}>
        <DataGrid
          rows={data}
          columns={columns}
          autoHeight={true}
          disableExtendRowFullWidth={true}
          disableColumnMenu={true}
          scrollbarSize={10}
          components={{
            Toolbar: GridToolbar,
          }}
        />
      </div>
      {selectedCategory && (
        <Modal open={open} onClose={handleClose}>
          <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
            <h2>Add Mark: </h2>
            <TextField label="Application Id" variant="outlined" value={applicationId} onChange={(e) => setApplicationId(e.target.value)} fullWidth sx={{ my: 2 }} />
            <TextField label="Marks" variant="outlined" value={marks} onChange={(e) => setMarks(e.target.value)} fullWidth sx={{ my: 2 }} />
            <TextField label="Comment" variant="outlined" value={comment} onChange={(e) => setComment(e.target.value)} fullWidth sx={{ mb: 2 }} />
            <TextField label="Remark" variant="outlined" value={remark} onChange={(e) => setRemark(e.target.value)} fullWidth sx={{ mb: 2 }} />
            <TextField
              select
              label="Category Name"
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
              fullWidth
              sx={{ mb: 2 }}
            >
              {categories.map((category) => (
                <MenuItem key={category.categoryId} value={category.categoryId}>
                  {category.categoryName}
                </MenuItem>
              ))}
            </TextField>
            <Button variant="contained" color="primary" onClick={handleSubmit}>
              Submit
            </Button>
          </Box>
        </Modal>
      )}
      <ToastContainer />
    </div>
  );
};

export default Marks;
