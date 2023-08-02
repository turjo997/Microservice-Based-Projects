import React, { useState } from 'react';
import axios from 'axios';
import jsPDF from 'jspdf'; 
import 'jspdf-autotable'; 
import './ApplicantRegister.scss';

const ApplicantRegister = () => {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [gender, setGender] = useState('');
    const [dob, setDob] = useState('');
    const [email, setEmail] = useState('');
    const [contactNumber, setContactNumber] = useState('');
    const [degreeName, setDegreeName] = useState('Bachelor\'s Degree');
    const [educationalInstitute, setEducationalInstitute] = useState('');
    const [cgpa, setCgpa] = useState('');
    const [passingYear, setPassingYear] = useState('');
    const [presentAddress, setPresentAddress] = useState('');

    const [photoFile, setPhotoFile] = useState(null);
    const [cvFile, setCvFile] = useState(null);

    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [applicantId, setApplicantId] = useState(null);



    const handleSubmit = (e) => {
        e.preventDefault();
        const applicantData = {
            firstName,
            lastName,
            gender,
            dob,
            email,
            contactNumber,
            degreeName,
            educationalInstitute,
            cgpa,
            passingYear,
            presentAddress,
        };

        const token = localStorage.getItem('token');

        axios.post('http://localhost:8080/applicant/', applicantData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => {
                setApplicantId(response.data.applicantId);
                setSuccessMessage('Applicant registered successfully.');
                setErrorMessage('');
                setFirstName(response.data.firstName);
                setLastName(response.data.lastName);
                setGender(response.data.gender);
                setDob(response.data.dob);
                setEmail(response.data.email);
                setContactNumber(response.data.contactNumber);
                setDegreeName(response.data.degreeName);
                setEducationalInstitute(response.data.educationalInstitute);
                setCgpa(response.data.cgpa);
                setPassingYear(response.data.passingYear);
                setPresentAddress(response.data.presentAddress);                
            })
            .catch((error) => {
                setErrorMessage('Error registering applicant. Please try again.');
                setSuccessMessage('');
            });
    };



    const handleSubmitResource = (e) => {
        e.preventDefault();

        if (applicantId) {            
            uploadFiles(applicantId);
        } else {
            setErrorMessage('Please register the applicant before uploading the resource.');
            setSuccessMessage('');
        }
    };


    const uploadFiles = (applicantId) => {
        const formData = new FormData();
        formData.append('photo', photoFile);
        formData.append('cv', cvFile);

        const token = localStorage.getItem('token');

        axios
            .post(`http://localhost:8080/resource/applicant/${applicantId}`, formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
            .then(() => {
                setSuccessMessage('Photo and CV uploaded successfully.');
                setErrorMessage('');
            })
            .catch((error) => {
                setErrorMessage('Error uploading photo and CV. Please try again.');
                setSuccessMessage('');
            });
    };



    const handleGeneratePDF = () => {
        if (applicantId) {
            const pdf = new jsPDF();

            const watermarkImageUrl = './src/assets/bjit.png';
            const watermarkWidth = 40;
            const watermarkHeight = (watermarkWidth * 21) / 20;
            const xPosition = (pdf.internal.pageSize.getWidth() - watermarkWidth) / 2;
            const yPosition = (pdf.internal.pageSize.getHeight() - watermarkHeight) / 30;
            pdf.addImage(watermarkImageUrl, 'PNG', xPosition, yPosition, watermarkWidth, watermarkHeight);

            const tableData = [
                ['Temporary ID', btoa(applicantId)],
                ['Full Name', `${firstName} ${lastName}`],
                ['Email', email],
                ['Contact Number', contactNumber],
                ['Degree Name', degreeName],
                ['CGPA', cgpa],
                ['Passing Year', passingYear],
                ['Present Address', presentAddress],
            ];

            pdf.autoTable({
                head: [['Attribute', 'Value']],
                body: tableData,
                startY: 60,
            });


            pdf.save('Applicant_card.pdf');
        }
    };


    return (
        <div className="applicant-register">
            <h2>Applicant Registration</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="firstName">First Name:</label>
                    <input
                        type="text"
                        id="firstName"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="lastName">Last Name:</label>
                    <input
                        type="text"
                        id="lastName"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="gender">Gender:</label>
                    <select
                        id="gender"
                        value={gender}
                        onChange={(e) => setGender(e.target.value)}
                        required
                    >
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div>
                    <label htmlFor="dob">Date of Birth:</label>
                    <input
                        type="date"
                        id="dob"
                        value={dob}
                        onChange={(e) => setDob(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="contactNumber">Contact Number:</label>
                    <input
                        type="tel"
                        id="contactNumber"
                        value={contactNumber}
                        onChange={(e) => setContactNumber(e.target.value)}
                        pattern="[0-9]{9,}"
                        required
                    />
                </div>
                <div>
                    <label htmlFor="degreeName">Degree Name:</label>
                    <select
                        id="degreeName"
                        value={degreeName}
                        onChange={(e) => setDegreeName(e.target.value)}
                        required
                    >
                        <option value="Bachelor's Degree">Bachelor's Degree</option>
                        <option value="Master's Degree">Master's Degree</option>
                        <option value="Ph.D.">Ph.D.</option>                        
                    </select>
                </div>
                <div>
                    <label htmlFor="educationalInstitute">Educational Institute:</label>
                    <input
                        type="text"
                        id="educationalInstitute"
                        value={educationalInstitute}
                        onChange={(e) => setEducationalInstitute(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="cgpa">CGPA:</label>
                    <input
                        type="number"
                        step="0.01"
                        id="cgpa"
                        value={cgpa}
                        onChange={(e) => setCgpa(e.target.value)}
                        min="0"
                        max="4"
                        required
                    />
                </div>
                <div>
                    <label htmlFor="passingYear">Passing Year:</label>
                    <input
                        type="number"
                        id="passingYear"
                        value={passingYear}
                        onChange={(e) => setPassingYear(e.target.value)}
                        min="1980"
                        max="2023"
                        required
                    />
                </div>
                <div>
                    <label htmlFor="presentAddress">Present Address:</label>
                    <textarea
                        id="presentAddress"
                        value={presentAddress}
                        onChange={(e) => setPresentAddress(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <button type="submit">Register</button>
                </div>


            </form>

            <form onSubmit={handleSubmitResource}>
                <div>
                    <label htmlFor="photo">Photo:</label>
                    <input
                        type="file"
                        id="photo"
                        onChange={(e) => setPhotoFile(e.target.files[0])}
                        accept="image/*"
                        required
                    />
                </div>
                <div>
                    <label htmlFor="cv">CV:</label>
                    <input
                        type="file"
                        id="cv"
                        onChange={(e) => setCvFile(e.target.files[0])}
                        accept=".pdf"
                        required
                    />
                </div>
                <div>
                    <button type="submit">Upload Resource</button>
                </div>

            </form>


            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {applicantId && (
                <div>
                    <p>Temporary ID: {btoa(applicantId)}</p>
                    <button onClick={handleGeneratePDF} style={{ background: 'blue' }}>Save as PDF</button>
                </div>
            )}
        </div>
    );
};

export default ApplicantRegister;
