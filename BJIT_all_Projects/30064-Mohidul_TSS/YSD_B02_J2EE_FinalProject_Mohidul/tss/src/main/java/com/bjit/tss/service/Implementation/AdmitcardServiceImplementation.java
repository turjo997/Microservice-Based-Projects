package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.AdmitcardEntity;
import com.bjit.tss.entity.ApprovalEntity;
import com.bjit.tss.entity.ResourceEntity;
import com.bjit.tss.model.AdmitcardModel;
import com.bjit.tss.repository.AdmitcardRepository;
import com.bjit.tss.repository.ApprovalRepository;
import com.bjit.tss.repository.ResourceRepository;
import com.bjit.tss.service.AdmitcardService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

//for pdf
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;


//for QR Code
import com.google.zxing.EncodeHintType;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;


@Service
@RequiredArgsConstructor
public class AdmitcardServiceImplementation implements AdmitcardService {

    private final AdmitcardRepository admitcardRepository;
    private final ApprovalRepository approvalRepository;


    @Override
    public ResponseEntity<Object> createAdmitcard(AdmitcardModel admitcardModel) { //automatic
        List<ApprovalEntity> approvedApprovals = approvalRepository.findByIsApprovedTrue();

        if (approvedApprovals.isEmpty()) {
            return ResponseEntity.badRequest().body("No approved approvals found.");
        }

        List<AdmitcardEntity> admitcards = new ArrayList<>();

        for (ApprovalEntity approvalEntity : approvedApprovals) {
            AdmitcardEntity admitcardEntity = new AdmitcardEntity();
            admitcardEntity.setCandidateId(approvalEntity);

            // Generate serial number (assuming it's system-generated)
            // Long generatedSerialNumber = generateSerialNumber(); // UUID based
            Long generatedSerialNumber = generateSerialNumberSerially(); // Sequence based
            admitcardEntity.setSerialNumber(generatedSerialNumber);

            admitcards.add(admitcardEntity);
        }

        List<AdmitcardEntity> savedAdmitcards = admitcardRepository.saveAll(admitcards);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmitcards);
    }


    @Override
    public ResponseEntity<Object> updateAdmitcard(Long admitcardId, AdmitcardModel admitcardModel) {
        Optional<AdmitcardEntity> optionalAdmitcard = admitcardRepository.findById(admitcardId);
        if (optionalAdmitcard.isPresent()) {
            AdmitcardEntity existingAdmitcard = optionalAdmitcard.get();

            Optional<ApprovalEntity> optionalApproval = approvalRepository.findById(admitcardModel.getCandidateId().getApprovalId());
            if (optionalApproval.isPresent()) {
                ApprovalEntity approvalEntity = optionalApproval.get();
                existingAdmitcard.setCandidateId(approvalEntity);
            } else {
                return ResponseEntity.notFound().build();
            }

            existingAdmitcard.setSerialNumber(existingAdmitcard.getSerialNumber()); // Assuming serialNumber is not updated in the update operation

            admitcardRepository.save(existingAdmitcard);
            return ResponseEntity.ok(existingAdmitcard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteAdmitcard(Long admitcardId) {
        Optional<AdmitcardEntity> optionalAdmitcard = admitcardRepository.findById(admitcardId);
        if (optionalAdmitcard.isPresent()) {
            AdmitcardEntity existingAdmitcard = optionalAdmitcard.get();
            admitcardRepository.delete(existingAdmitcard);
            return ResponseEntity.ok(existingAdmitcard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllAdmitcards() {
        List<AdmitcardEntity> admitcards = admitcardRepository.findAll();
        return ResponseEntity.ok(admitcards);
    }

    @Override
    public ResponseEntity<Object> getAdmitcardById(Long applicantId) {

        Optional<AdmitcardEntity> optionalAdmitcard = admitcardRepository.findByApplicantId(applicantId);


        if (optionalAdmitcard.isPresent()) {
            AdmitcardEntity admitcard = optionalAdmitcard.get();
            return ResponseEntity.ok(admitcard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> generateAdmitcardPdf(Long applicantId) {
        Optional<AdmitcardEntity> optionalAdmitcard = admitcardRepository.findByApplicantId(applicantId);
        if (optionalAdmitcard.isPresent()) {
            AdmitcardEntity admitcard = optionalAdmitcard.get();

            byte[] pdfBytes = generateAdmitcardPdfBytes(admitcard);

            // Return the PDF as a response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "admitcard.pdf");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UUID-based serial number generation
    private Long generateSerialNumber() {
        // For simplicity, we assume it's a UUID-based serial number
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    //////////////////////////////////////////////////////////////////////////////////
    // generate a unique serial number
    private AtomicLong counter = new AtomicLong(10000);
    private Long generateSerialNumberSerially() {
        return counter.incrementAndGet();
    }
    /////////////////////////////////////////////////////////////////////////////////


    private byte[] generateAdmitcardPdfBytes22(AdmitcardEntity admitcard) {
        // Generating a dummy PDF with the admit card details and QR code
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
             Document document = new Document(pdfDocument)) {

            // Retrieve the Exam name from circularId
            String examName = admitcard.getCandidateId().getCircular().getTitle();

            // Retrieve the candidate name
            String candidateName = admitcard.getCandidateId().getApplicant().getFirstName() + " " + admitcard.getCandidateId().getApplicant().getLastName();

            // Retrieve the serial number
            Long serialNumber = admitcard.getSerialNumber();

            // Add content to the PDF document
            document.add(new Paragraph("Admit Card\n"));
            document.add(new Paragraph("Exam Name: " + examName));
            document.add(new Paragraph("Candidate Name: " + candidateName));
            document.add(new Paragraph("Serial Number: " + serialNumber));
            document.add(new Paragraph("Details QR Code: \n"));


            // Generate QR code containing information about the applicant
            String qrCodeContent = "Applicant:\n" +
                    "Name: " + admitcard.getCandidateId().getApplicant().getFirstName() + " " + admitcard.getCandidateId().getApplicant().getLastName() + "\n" +
                    "Gender: " + admitcard.getCandidateId().getApplicant().getGender() + "\n" +
                    "Date of Birth: " + admitcard.getCandidateId().getApplicant().getDob() + "\n" +
                    "Email: " + admitcard.getCandidateId().getApplicant().getEmail() + "\n" +
                    "Contact Number: " + admitcard.getCandidateId().getApplicant().getContactNumber() + "\n" +
                    "Degree Name: " + admitcard.getCandidateId().getApplicant().getDegreeName() + "\n" +
                    "Educational Institute: " + admitcard.getCandidateId().getApplicant().getEducationalInstitute() + "\n" +
                    "CGPA: " + admitcard.getCandidateId().getApplicant().getCgpa() + "\n" +
                    "Passing Year: " + admitcard.getCandidateId().getApplicant().getPassingYear() + "\n" +
                    "Present Address: " + admitcard.getCandidateId().getApplicant().getPresentAddress();


            byte[] qrCodeBytes = generateQRCode(qrCodeContent);
            if (qrCodeBytes != null) {
                // Embed the QR code image in the PDF document
                Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeBytes));
                document.add(qrCodeImage);
            }

            // Save and close the PDF document
            pdfDocument.close();

            // Retrieve the byte array of the generated PDF
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private final ResourceRepository resourceRepository;

    public byte[] generateAdmitcardPdfBytes(AdmitcardEntity admitcard) {
        // Generating a dummy PDF with the admit card details and QR code
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
             Document document = new Document(pdfDocument, PageSize.A4)) {

            String examName = admitcard.getCandidateId().getCircular().getTitle();

            String candidateName = admitcard.getCandidateId().getApplicant().getFirstName() + " " + admitcard.getCandidateId().getApplicant().getLastName();

            Long serialNumber = admitcard.getSerialNumber();
            Long aplcntId = admitcard.getCandidateId().getApplicant().getApplicantId();

            document.add(new Paragraph("Written Test").setFontSize(20).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Admit Card").setFontSize(18).setTextAlignment(TextAlignment.CENTER));


            document.add(new Paragraph("No. " + serialNumber).setTextAlignment(TextAlignment.CENTER));


            ResourceEntity resourceEntity = resourceRepository.findByApplicant_ApplicantId(admitcard.getCandidateId().getApplicant().getApplicantId())
                    .orElse(null);

            if (resourceEntity != null && resourceEntity.getPhotoData() != null) {
                byte[] photoData = resourceEntity.getPhotoData();

                float desiredWidth = 100f;
                float desiredHeight = 150f;

                Image photoImage = new Image(ImageDataFactory.create(photoData))
                        .setWidth(desiredWidth)
                        .setHeight(desiredHeight)
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT);

                document.add(photoImage);
            }

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Applicant Info: \n").setFontSize(14));

            Table table = new Table(2);

            // Add rows to the table
            table.addCell("Applicant Id.  ");
            table.addCell(new Paragraph(String.valueOf(aplcntId)));

            table.addCell("Name  ");
            table.addCell(admitcard.getCandidateId().getApplicant().getFirstName() + " " + admitcard.getCandidateId().getApplicant().getLastName());

            table.addCell("Gender  ");
            table.addCell(admitcard.getCandidateId().getApplicant().getGender());

            table.addCell("Email  ");
            table.addCell(admitcard.getCandidateId().getApplicant().getEmail());

            table.addCell("Exam  ");
            table.addCell(examName);

            table.addCell("Exam vanue ");
            table.addCell("BJIT Acacdemy Building.");

            // Set alignment properties for the table
            table.setFontSize(12);
            table.setTextAlignment(TextAlignment.LEFT);
            table.setHorizontalAlignment(HorizontalAlignment.LEFT);
            table.setVerticalAlignment(VerticalAlignment.MIDDLE);

            Border noBorder = new SolidBorder(0f);
            table.setBorder(noBorder);

            document.add(table);

            document.add(new Paragraph("\n"));

//            document.add(new Paragraph("\n\nDetails QR Code: \n").setFontSize(14).setTextAlignment(TextAlignment.CENTER));

            String qrCodeContent = "BJIT ACADEMY \nApplicant:\n" +
                    "Serial Number: " + serialNumber + "\n" +
                    "Name: " + admitcard.getCandidateId().getApplicant().getFirstName() + " " + admitcard.getCandidateId().getApplicant().getLastName() + "\n" +
                    "Gender: " + admitcard.getCandidateId().getApplicant().getGender() + "\n" +
                    "Date of Birth: " + admitcard.getCandidateId().getApplicant().getDob() + "\n" +
                    "Email: " + admitcard.getCandidateId().getApplicant().getEmail() + "\n" +
                    "Contact Number: " + admitcard.getCandidateId().getApplicant().getContactNumber() + "\n" +
                    "Degree Name: " + admitcard.getCandidateId().getApplicant().getDegreeName() + "\n" +
                    "Educational Institute: " + admitcard.getCandidateId().getApplicant().getEducationalInstitute() + "\n" +
                    "Passing Year: " + admitcard.getCandidateId().getApplicant().getPassingYear();

            byte[] qrCodeBytes = generateQRCode(qrCodeContent);
            if (qrCodeBytes != null) {
                Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeBytes));
                document.add(qrCodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER));
            }

            document.add(new Paragraph("BJIT ACADEMY\n").setFontSize(18).setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Instruction Set: \n").setFontSize(12));
            document.add(new Paragraph("1. Bring this admit card along with a valid photo ID for verification on the exam day.\n"));
            document.add(new Paragraph("2. Do not carry any electronic devices or study materials to the exam hall.\n"));

            pdfDocument.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private byte[] generateQRCode(String content) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);

            // Convert the matrix to a BufferedImage
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Convert the image to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
