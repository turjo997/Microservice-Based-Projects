package com.bjit.TraineeSelection.service.impl;

import com.bjit.TraineeSelection.entity.ApplicantEntity;
import com.bjit.TraineeSelection.entity.Application;
import com.bjit.TraineeSelection.entity.Circular;
import com.bjit.TraineeSelection.exception.ApplicationAlreadyExistsException;
import com.bjit.TraineeSelection.model.ApplicationDto;
import com.bjit.TraineeSelection.repository.ApplicantRepository;
import com.bjit.TraineeSelection.repository.ApplicationRepository;
import com.bjit.TraineeSelection.repository.CircularRepository;
import com.bjit.TraineeSelection.service.ApplicantService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicationRepository applicationRepository;

    private final ApplicantRepository applicantRepository;
    private final CircularRepository circularRepository;


    private final ModelMapper modelMapper;



    @Override
    public ResponseEntity<Object> applyCircular(ApplicationDto applicationDto) throws ApplicationAlreadyExistsException {
        Long newapplicantId = applicationDto.getApplicantId();
        Long newcircularId = applicationDto.getCircularId();

        // Check if an application for the applicantId and circularId already exists in the database
        if (applicationRepository.existsByApplicantEntityApplicantIdAndCircularCircularId(newapplicantId, newcircularId)) {
            // Throw a custom exception indicating that the application already exists
            throw new ApplicationAlreadyExistsException("Applicant already applied to this circular");
        }
        Application application = new Application();

        Circular circular = new Circular();
        circular.setCircularId(applicationDto.getCircularId());
        application.setCircular(circular);

        ApplicantEntity applicantEntity = new ApplicantEntity();
        applicantEntity.setApplicantId(applicationDto.getApplicantId());
        application.setApplicantEntity(applicantEntity);

        Application savedApplication = applicationRepository.save(application);

        ApplicationDto newApplicationDto = new ApplicationDto();
        newApplicationDto.setApplicationId(savedApplication.getApplicationId());
        newApplicationDto.setApprovalStatus(savedApplication.isApprovalStatus());
        newApplicationDto.setCircularId(savedApplication.getCircular().getCircularId());
        newApplicationDto.setApplicantId(savedApplication.getApplicantEntity().getApplicantId());

        return new ResponseEntity<>(newApplicationDto, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> viewCircular(Long circularId) {
       Object circular= circularRepository.findById(circularId);
       return new ResponseEntity<>(circular,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Circular>> getAllCirculars() {
        List<Circular> activeCirculars = circularRepository.findByStatus("active");
        return new ResponseEntity<>(activeCirculars, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getApplicantId(String email) {
        ApplicantEntity applicantEntity=applicantRepository.findByEmail(email);
        Long applicantId = applicantEntity.getApplicantId();
        return new ResponseEntity<>(applicantId, HttpStatus.OK);
    }

    @Override
    public List<Application> ApplicantApplications(Long applicantId) {
        List<Application> applicationsList=applicationRepository.findByApplicantEntity_ApplicantId(applicantId);
        return applicationsList;
    }

    public ResponseEntity<byte[]> getAdmitCard(Long applicantId) {
        try {
            // Find the applicant by applicantId
            Optional<ApplicantEntity> applicantOptional = applicantRepository.findById(applicantId);
            if (applicantOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Applicant not found".getBytes());
            }

            // Retrieve the approved application for the applicant
            Optional<Application> applicationOptional = applicationRepository.findByApplicantEntityAndApprovalStatus(applicantOptional.get(), true);
            if (applicationOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Approved application not found".getBytes());
            }

            // Create a new PDF document
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Write the document content to the output stream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            // Generate the admit card content
            generateAdmitCardContent(document, applicantOptional.get(), applicationOptional.get());

            document.close();
            writer.close();

            // Set the response headers for the admit card file
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "admit_card_" + applicantId + ".pdf");

            // Return the admit card bytes in the response body with the appropriate headers
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any exceptions that occur during admit card generation
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating admit card".getBytes());
        }
    }

    @Override
    public void uploadImageAndCv(Long applicantId, MultipartFile image, MultipartFile cv) throws IOException {
        byte[] imageBytes = image.getBytes();
        byte[] cvBytes = cv.getBytes();


        Optional<ApplicantEntity> optionalEntity = applicantRepository.findById(applicantId);

        if (optionalEntity.isPresent()) {
            // If a record exists, update the image and CV fields
            ApplicantEntity applicantEntity = optionalEntity.get();
            applicantEntity.setImage(imageBytes);
            applicantEntity.setCv(cvBytes);
            applicantRepository.save(applicantEntity);

        }
    }




    private void generateAdmitCardContent(Document document, ApplicantEntity applicant, Application application) throws DocumentException, IOException {
        // Add applicant ID
        Paragraph applicantIdParagraph = new Paragraph();
        long serialNumber = generateSerialNumber(applicant.getApplicantId()); // Generate unique serial number based on applicant's ID
        applicantIdParagraph.add("Serial Number: " + serialNumber + "\n");
        applicantIdParagraph.add("Name: " + applicant.getFirstName() + " " + applicant.getLastName() + "\n");
        applicantIdParagraph.add("Circular: " + application.getCircular().getCircularName() + "\n");
        applicantIdParagraph.add("Gender: " + applicant.getGender() + "\n");
        applicantIdParagraph.add("Date of Birth: " + applicant.getDob() + "\n");

        document.add(applicantIdParagraph);

        // Generate and add QR code image
        String qrCodeText = applicant.toString(); // Modify this to convert ApplicantEntity to a string representation
        Image qrCodeImage = generateQRCodeImage(qrCodeText);
        if (qrCodeImage != null) {
            document.add(qrCodeImage);
        }
    }





    // Generate the QR code image
    private Image generateQRCodeImage(String qrCodeText) throws IOException, DocumentException {
        int width = 200;
        int height = 200;
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream qrCodeStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qrCodeStream);

        // Create an Image object from the QR code image stream
        byte[] qrCodeBytes = qrCodeStream.toByteArray();
        Image qrCodeImage = Image.getInstance(qrCodeBytes);

        // Scale the QR code image to fit the desired size in the document
        qrCodeImage.scaleToFit(100, 100); // Adjust the dimensions as per your requirements

        return qrCodeImage;
    }

    private long generateSerialNumber(Long applicantId) {
        int primeNumber = 999983;
        long serialNumber = applicantId % primeNumber;

        if (serialNumber < 100_000L) {
            serialNumber += primeNumber;
        }

        return serialNumber;
    }








}
