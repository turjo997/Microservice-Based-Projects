package com.bjit.TraineeSelection.service.impl;

import com.bjit.TraineeSelection.entity.AssignEvaluator;
import com.bjit.TraineeSelection.exception.EvaluatorAlreadyAssignedException;
import com.bjit.TraineeSelection.entity.*;
import com.bjit.TraineeSelection.exception.ApplicantAlreadyApprovedException;
import com.bjit.TraineeSelection.exception.CircularExist;
import com.bjit.TraineeSelection.exception.EmailAlreadyTakenException;
import com.bjit.TraineeSelection.exception.ResourceNotFoundException;
import com.bjit.TraineeSelection.model.*;
import com.bjit.TraineeSelection.repository.*;
import com.bjit.TraineeSelection.service.AdminService;
import com.bjit.TraineeSelection.utils.JwtService;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CircularRepository circularRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;
    private final MarkRepository markRepository;
    private final AssignEvaluatorRepository assignEvaluatorRepository;
    private final EvaluatorRepository evaluatorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final MailRepository mailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Set<Long> generatedCodes = new HashSet<>();
    private final Random random = new Random();
    @Override
    public ResponseEntity<Object> createCircular(CircularDto circularDto) {

        Long circularId = circularDto.getCircularId();
        if (circularId != null && circularRepository.existsById(circularId)) {
             throw new CircularExist("Circular Already Exist");
        }

        Circular circular=this.dtoToCircular(circularDto);
        Circular savedCircular=circularRepository.save(circular);

        CircularDto newCircularDto=circularToDto(savedCircular);
        return new ResponseEntity<>(newCircularDto, HttpStatus.OK);


    }


    @Override
    public ResponseEntity<List<Circular>> getCirculars() {
        return new ResponseEntity<>(circularRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getCircularByIds(Long circularId) {
        return new ResponseEntity<>(this.circularRepository.findById(circularId),HttpStatus.OK);
    }

    public ResponseEntity<List<Application>> getAllApprovedApplication() {
        List<Application> approvedApplications = applicationRepository.findByApprovalStatus(true);

        return new ResponseEntity<>(approvedApplications,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> registerEvaluator(EvaluatorDto evaluatorDto) {

        String email = evaluatorDto.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyTakenException("Email is already taken");
        }

        Role desiredRole = Role.EVALUATOR;
        UserEntity userEntity = UserEntity.builder()
                .email(evaluatorDto.getEmail())
                .password(passwordEncoder.encode(evaluatorDto.getPassword())) // Corrected placement of closing parenthesis
                .role(desiredRole)
                .build();
        userRepository.save(userEntity);

        Long userId = userEntity.getUserId();

        EvaluatorEntity evaluatorEntity=EvaluatorEntity.builder()
                .firstName(evaluatorDto.getFirstName())
                .lastName(evaluatorDto.getLastName())
                .gender(evaluatorDto.getGender())
                .dob(evaluatorDto.getDob())
                .email(evaluatorDto.getEmail())
                .contactNumber(evaluatorDto.getContactNumber())
                .specialization(evaluatorDto.getSpecialization())
                .userId(userId)
                .build();
        evaluatorRepository.save(evaluatorEntity);
        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token(jwtService.generateToken(userEntity))
                .build();
        return new ResponseEntity<>(authRes, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> showAllEvaluators() {
        List<EvaluatorEntity> evaluatorEntities = evaluatorRepository.findAll();

        List<EvaluatorPrimaryDto> evaluatorPrimaryDtos = evaluatorEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(evaluatorPrimaryDtos);
    }

    private EvaluatorPrimaryDto mapToDto(EvaluatorEntity evaluatorEntity) {
        return EvaluatorPrimaryDto.builder()
                .evaluatorId(evaluatorEntity.getEvaluatorId())
                .firstName(evaluatorEntity.getFirstName())
                .lastName(evaluatorEntity.getLastName())
                .specialization(evaluatorEntity.getSpecialization())
                .build();
    }

    @Override
    public ResponseEntity<Object> getEvaluatorById(Long evaluatorId) {
        EvaluatorEntity evaluatorEntity=this.evaluatorRepository.findById(evaluatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Circular not found with ID: "));

      return new ResponseEntity<>(evaluatorEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> findApplicant(Long applicantId) {
        Marks marks = this.markRepository.findByApplicantId(applicantId);
        if (marks == null) {
            throw new ResourceNotFoundException("Applicant Not found");
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Marks>> showAllFinalCandidate() {
        List<Marks> finalCandidates = markRepository.findByStatus(4L);
        return new ResponseEntity<>(finalCandidates, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Object> assignEvaluator(AssignEvaluatorDto assignEvaluatorDto) {
        Long evaluatorId=assignEvaluatorDto.getEvaluatorId();
        Long circularId=assignEvaluatorDto.getCircularId();
        if (assignEvaluatorRepository.existsByEvaluatorIdAndCircularId(evaluatorId, circularId)) {
            throw new EvaluatorAlreadyAssignedException("Evaluator is already assigned to this circular.");
        }
        AssignEvaluator assignEvaluator=this.dtoToAssignEvaluator(assignEvaluatorDto);
        AssignEvaluator savedAssignEvaluator=assignEvaluatorRepository.save(assignEvaluator);
        AssignEvaluatorDto assignEvaluatorDto1=assignEvaluatorToDto(savedAssignEvaluator);

        return new ResponseEntity<>("Evaluator is Assigned",HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Object> updateCircular(CircularDto circularDto) {
        Circular circular=new Circular();
        circular.setCircularId(circularDto.getCircularId());
        circular.setCircularName(circularDto.getCircularName());
        circular.setDetail(circularDto.getDetail());
        circular.setStatus(circularDto.getStatus());
        Circular updateCircular=circularRepository.save(circular);
        return new ResponseEntity<>(updateCircular,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> approveApplicant(Long applicationId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Long applicantId = application.getApplicantEntity().getApplicantId();
            String applicantEmail=application.getApplicantEntity().getEmail();

            // Check if the applicant is already approved in any cir cular
            boolean isAlreadyApproved = applicationRepository.existsByApplicantEntity_ApplicantIdAndApprovalStatusTrue(applicantId);
            if (isAlreadyApproved) {
                throw new DataIntegrityViolationException("Applicant already approved in a circular");
            }

            Marks marks = new Marks();
            marks.setStatus(1L);
            marks.setApplicantId(applicantId);
            marks.setCircularId(application.getCircular().getCircularId());
            marks.setPaperCode(generateUniqueCode());
            marks.setWrittenMarks(0L);
            marks.setHrMarks(0L);
            marks.setTechnicalMarks(0L);
            marks.setTotalScore(0L);
            markRepository.save(marks);

            application.setApprovalStatus(true);
            applicationRepository.save(application); // Save the updated entity
            return new ResponseEntity<>("Applicant Approved", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Application not found", HttpStatus.NOT_FOUND);
        }
    }




    private Long generateUniqueCode() {
        long min = 100_000L; // Minimum 6-digit value (100000)
        long max = 999_999L; // Maximum 6-digit value (999999)
        long range = max - min + 1;
        long uniqueCode;

        do {
            uniqueCode = min + (long) (random.nextDouble() * range); // Generate a random number within the range
        } while (generatedCodes.contains(uniqueCode));

        generatedCodes.add(uniqueCode);
        return uniqueCode;
    }

    //showAll Applicant
    @Override
    public ResponseEntity<Object> showAll() {
        List<ApplicantEntity> applicantEntityList=applicantRepository.findAll();

        return new ResponseEntity<>(applicantEntityList,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Object> uploadMark(MarksDto marksDto) {
        Long applicantId = marksDto.getApplicantId();
        Marks existingMarks = markRepository.findByApplicantId(applicantId);

        if (existingMarks != null) {
            return new ResponseEntity<>("Applicant ID should be unique", HttpStatus.BAD_REQUEST);
        }

        Marks marks1 = new Marks();
        Long state=marks1.getStatus();
        marks1.setPaperCode(marksDto.getPaperCode());
        marks1.setWrittenMarks(marksDto.getWrittenMarks());
        marks1.setTechnicalMarks(marksDto.getTechnicalMarks());
        marks1.setHrMarks(marksDto.getHrMarks());

        Marks savedMarks = markRepository.save(marks1);

        return new ResponseEntity<>("Marks Uploaded", HttpStatus.OK);
    }



//    @Override
//    public ResponseEntity<Object> updateMark(Long applicantId, MarksDto marksDto) {
//        Marks marks2=markRepository.findByApplicantId(applicantId);
//        marks2.setCircularId(marksDto.getCircularId());
//        marks2.setPaperCode(marksDto.getPaperCode());
//        marks2.setWrittenMarks(marksDto.getWrittenMarks());
//        marks2.setTechnicalMarks(marksDto.getTechnicalMarks());
//        marks2.setHrMarks(marksDto.getHrMarks());
//
//       Marks savedMarks= markRepository.save(marks2);
//
//        return new ResponseEntity<>("Marks Updated",HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<Object> updateMark(Long applicantId, MarksDto marksDto) {
        Marks marks2 = markRepository.findByApplicantId(applicantId);

        Long currentStatus = marks2.getStatus();

        if (currentStatus == 1) {
            Long writtenMarks = marksDto.getWrittenMarks();
            if (writtenMarks != null && writtenMarks >= 0) {
                marks2.setWrittenMarks(writtenMarks);

                if (writtenMarks > 50) {
                    marks2.setStatus(currentStatus + 1);
                }
            } else {
                return new ResponseEntity<>("Mark Upload Failed", HttpStatus.BAD_REQUEST);
            }
        } else if (currentStatus == 2) {
            Long technicalMarks = marksDto.getTechnicalMarks();
            if (technicalMarks != null && technicalMarks >= 0) {
                marks2.setTechnicalMarks(technicalMarks);
                if (technicalMarks > 50) {
                    marks2.setStatus(currentStatus + 1);
                }
            } else {
                return new ResponseEntity<>("Mark Upload Failed", HttpStatus.BAD_REQUEST);
            }
        } else if (currentStatus == 3 || currentStatus==4) {
            Long hrMarks = marksDto.getHrMarks();
            if (hrMarks != null && hrMarks >= 0) {
                marks2.setHrMarks(hrMarks);

                if (hrMarks > 50) {
                    marks2.setStatus(currentStatus + 1);
                }
            } else {
                return new ResponseEntity<>("Mark Upload Failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Mark Upload Failed", HttpStatus.BAD_REQUEST);
        }

        marks2.setTotalScore(marks2.getWrittenMarks()+marks2.getTechnicalMarks()+marks2.getHrMarks());

        Marks savedMarks = markRepository.save(marks2);
        return new ResponseEntity<>("Marks Updated", HttpStatus.OK);
    }








    @Override
    public ResponseEntity<Object> viewAllApplicationsByCircular(Long circularId) {
        Optional<Circular> circular = circularRepository.findById(circularId);
        if (circular == null) {
            return new ResponseEntity<>("Circular not found", HttpStatus.NOT_FOUND);
        }

        List<Application> applications = applicationRepository.findByCircular(circular);

        List<AllApplicationDto> allApplicationDtos = new ArrayList<>();
        for (Application application : applications) {
            AllApplicationDto allApplicationDto = new AllApplicationDto();
            allApplicationDto.setApplicationId(application.getApplicationId());
            allApplicationDto.setApprovalStatus(application.isApprovalStatus());
            allApplicationDto.setCircularId(circular.get().getCircularId());
            allApplicationDto.setCircularName(circular.get().getCircularName());
//            allApplicationDto.setCircularDetail(circular.getDetail());
            // Set other properties of ApplicationDto as needed

            // Retrieve ApplicantEntity data
            ApplicantEntity applicantEntity = application.getApplicantEntity();
            if (applicantEntity != null) {
                allApplicationDto.setApplicantId(applicantEntity.getApplicantId());
                // Set all properties of ApplicantEntity
                allApplicationDto.setFirstName(applicantEntity.getFirstName());
                allApplicationDto.setLastName(applicantEntity.getLastName());
                allApplicationDto.setGender(applicantEntity.getGender());
                allApplicationDto.setDob(applicantEntity.getDob());
                allApplicationDto.setEmail(applicantEntity.getEmail());
                allApplicationDto.setContactNumber(applicantEntity.getContactNumber());
                allApplicationDto.setDegreeName(applicantEntity.getDegreeName());
                allApplicationDto.setEducationalInstitute(applicantEntity.getEducationalInstitute());
                allApplicationDto.setCgpa(applicantEntity.getCgpa());
                allApplicationDto.setPassingYear(applicantEntity.getPassingYear());
                allApplicationDto.setAddress(applicantEntity.getAddress());

                allApplicationDtos.add(allApplicationDto);
            }
        }

        Map<Optional<Circular>, List<AllApplicationDto>> circularApplicationsMap = new HashMap<>();
        circularApplicationsMap.put(circular, allApplicationDtos);

        return new ResponseEntity<>(circularApplicationsMap, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> showAllApplication() {
        List<Application> applications=applicationRepository.findAll();

        return new ResponseEntity<>(applications,HttpStatus.OK);
    }

    // Generate the admit card for a specific applicant
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



    // Generate the admit card content
    private void generateAdmitCardContent(Document document, ApplicantEntity applicant, Application application) throws DocumentException, IOException {
        // Add applicant ID
        Paragraph applicantIdParagraph = new Paragraph();
        applicantIdParagraph.add("Serial Number: "+generateSerialNumber()+"\n");
        applicantIdParagraph.add("Name: " + applicant.getFirstName()+" "+applicant.getLastName()+"\n");
        applicantIdParagraph.add("Circular: "+application.getCircular().getCircularName()+"\n");
        applicantIdParagraph.add("Gender: "+applicant.getGender()+"\n");
        applicantIdParagraph.add("Date of Birth: "+applicant.getDob()+"\n");

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

    private long generateSerialNumber() {
        // Generate a unique 6-digit serial number
        Random random = new Random();
        long lowerBound = 100_000L; // Lower bound of 6-digit numbers
        long upperBound = 999_999L; // Upper bound of 6-digit numbers
        return random.nextLong() % (upperBound - lowerBound + 1) + lowerBound;
    }



    private Circular dtoToCircular(CircularDto circularDto)
    {
        Circular circular=this.modelMapper.map(circularDto,Circular.class);
        return circular;
    }

    private CircularDto circularToDto(Circular circular)
    {
        CircularDto circularDto=this.modelMapper.map(circular,CircularDto.class);
        return circularDto;
    }

    private AssignEvaluator dtoToAssignEvaluator(AssignEvaluatorDto assignEvaluatorDto)
    {
        AssignEvaluator evaluator=this.modelMapper.map(assignEvaluatorDto,AssignEvaluator.class);
        return evaluator;
    }

    private AssignEvaluatorDto assignEvaluatorToDto(AssignEvaluator assignEvaluator)
    {
        AssignEvaluatorDto assignEvaluatorDto=this.modelMapper.map(assignEvaluator,AssignEvaluatorDto.class);
        return assignEvaluatorDto;
    }
}
