package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.AdmitcardEntity;
import com.bjit.tss.entity.WrittenTestEntity;
import com.bjit.tss.model.WrittenTestModel;
import com.bjit.tss.repository.AdmitcardRepository;
import com.bjit.tss.repository.WrittenTestRepository;
import com.bjit.tss.service.WrittenTestService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WrittenTestServiceImplementation implements WrittenTestService {

    private final WrittenTestRepository writtenTestRepository;
    private final AdmitcardRepository admitCardRepository;


    @Override
    public ResponseEntity<Object> getWrittenTestById(Long writtenTestId) {
        WrittenTestEntity writtenTest = writtenTestRepository.findById(writtenTestId).orElse(null);
        if (writtenTest == null) {
            return new ResponseEntity<>("Written test not found", HttpStatus.NOT_FOUND);
        }
        WrittenTestModel writtenTestModel = convertToModel(writtenTest);
        return new ResponseEntity<>(writtenTestModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllWrittenTest() {
        List<WrittenTestEntity> writtenTests = writtenTestRepository.findAll();
        List<WrittenTestModel> writtenTestModels = writtenTests.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
        return new ResponseEntity<>(writtenTestModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createWrittenTest(WrittenTestModel writtenTestModel) {
        WrittenTestEntity writtenTest = new WrittenTestEntity();
        writtenTest.setHiddenCode(writtenTestModel.getHiddenCode());
        writtenTest.setApplicantId(writtenTestModel.getApplicantId());
        writtenTest.setMark(writtenTestModel.getMark());
        writtenTest.setCircular(writtenTest.getCircular());

        WrittenTestEntity savedWrittenTest = writtenTestRepository.save(writtenTest);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedWrittenTest);
    }


    @Override
    public ResponseEntity<Object> updateWrittenTest(Long writtenTestId, WrittenTestModel writtenTestModel) {
        WrittenTestEntity existingWrittenTest = writtenTestRepository.findById(writtenTestId).orElse(null);
        if (existingWrittenTest == null) {
            return new ResponseEntity<>("Written test not found", HttpStatus.NOT_FOUND);
        }
        existingWrittenTest.setHiddenCode(writtenTestModel.getHiddenCode());
        existingWrittenTest.setApplicantId(writtenTestModel.getApplicantId());
        existingWrittenTest.setMark(writtenTestModel.getMark());
        WrittenTestEntity updatedWrittenTest = writtenTestRepository.save(existingWrittenTest);
        WrittenTestModel updatedWrittenTestModel = convertToModel(updatedWrittenTest);
        return new ResponseEntity<>(updatedWrittenTestModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteWrittenTest(Long writtenTestId) {
        WrittenTestEntity writtenTest = writtenTestRepository.findById(writtenTestId).orElse(null);
        if (writtenTest == null) {
            return new ResponseEntity<>("Written test not found", HttpStatus.NOT_FOUND);
        }
        writtenTestRepository.delete(writtenTest);
        return new ResponseEntity<>("Written test deleted successfully", HttpStatus.OK);
    }

    private WrittenTestModel convertToModel(WrittenTestEntity writtenTestEntity) {
        WrittenTestModel writtenTestModel = new WrittenTestModel();
        writtenTestModel.setWrittenId(writtenTestEntity.getWrittenId());
        writtenTestModel.setHiddenCode(writtenTestEntity.getHiddenCode());
        writtenTestModel.setApplicantId(writtenTestEntity.getApplicantId());
        writtenTestModel.setMark(writtenTestEntity.getMark());
        writtenTestModel.setCircular(writtenTestEntity.getCircular());
        return writtenTestModel;
    }

    private WrittenTestEntity convertToEntity(WrittenTestModel writtenTestModel) {
        WrittenTestEntity writtenTestEntity = new WrittenTestEntity();
        writtenTestEntity.setWrittenId(writtenTestModel.getWrittenId());
        writtenTestEntity.setHiddenCode(writtenTestModel.getHiddenCode());
        writtenTestEntity.setApplicantId(writtenTestModel.getApplicantId());
        writtenTestEntity.setMark(writtenTestModel.getMark());
        return writtenTestEntity;
    }
    @Override
    public ResponseEntity<Object> autoCreateWrittenTest() {
        List<AdmitcardEntity> admitCards = admitCardRepository.findAll();
        if (admitCards.isEmpty()) {
            return new ResponseEntity<>("No admit cards found", HttpStatus.NOT_FOUND);
        }

        List<WrittenTestModel> createdWrittenTests = new ArrayList<>();

        for (AdmitcardEntity admitCard : admitCards) {
            Long applicantId = admitCard.getCandidateId().getApplicant().getApplicantId();

            // Generate hiddenCode
            Long hiddenCode = generateSerialNumberSerially();

            WrittenTestEntity writtenTest = new WrittenTestEntity();
            writtenTest.setApplicantId(applicantId);
            writtenTest.setHiddenCode(hiddenCode);
            writtenTest.setMark(0.0);
            writtenTest.setCircular(admitCard.getCandidateId().getCircular().getTitle());

            WrittenTestEntity savedWrittenTest = writtenTestRepository.save(writtenTest);
            WrittenTestModel savedWrittenTestModel = convertToModel(savedWrittenTest);
            createdWrittenTests.add(savedWrittenTestModel);
        }

        return new ResponseEntity<>(createdWrittenTests, HttpStatus.CREATED);
    }


    private AtomicLong counter = new AtomicLong(100);

    private Long generateSerialNumberSerially() {
        return counter.incrementAndGet();
    }

    @Override
    public ResponseEntity<Object> updateWrittenTest(Long hiddenCode, Double mark) {
        WrittenTestEntity existingWrittenTest = writtenTestRepository.findByHiddenCode(hiddenCode);
        if (existingWrittenTest == null) {
            return new ResponseEntity<>("Invalid hiddenCode", HttpStatus.NOT_FOUND);
        }

        existingWrittenTest.setMark(mark);

        WrittenTestEntity updatedWrittenTest = writtenTestRepository.save(existingWrittenTest);
        WrittenTestModel updatedWrittenTestModel = convertToModel(updatedWrittenTest);
        return new ResponseEntity<>(updatedWrittenTestModel, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Object> uploadWrittenTestByHiddenCode(MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);

            List<WrittenTestModel> updatedWrittenTests = new ArrayList<>();

            for (Row row : sheet) {
                Long hiddenCode = readHiddenCodeFromExcel(row);
                Double mark = readMarkFromExcel(row);

                if (hiddenCode != null && mark != null) {
                    updateWrittenTest(hiddenCode, mark);

                    WrittenTestEntity updatedWrittenTest = writtenTestRepository.findByHiddenCode(hiddenCode);
                    if (updatedWrittenTest != null) {
                        WrittenTestModel updatedWrittenTestModel = convertToModel(updatedWrittenTest);
                        updatedWrittenTests.add(updatedWrittenTestModel);
                    }
                }
            }

            return new ResponseEntity<>(updatedWrittenTests, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process the Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getWrittenTestByApplicantId(Long applicantId) {
        WrittenTestEntity writtenTest = (WrittenTestEntity) writtenTestRepository.findByApplicantId(applicantId).orElse(null);
        if (writtenTest == null) {
            return new ResponseEntity<>("Written test not found", HttpStatus.NOT_FOUND);
        }
        WrittenTestModel writtenTestModel = convertToModel(writtenTest);
        return new ResponseEntity<>(writtenTestModel, HttpStatus.OK);
    }


    private Long readHiddenCodeFromExcel(Row row) {
        Cell cell = row.getCell(0); // Assuming the hiddenCode is in the first column
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        }
        return null; // HiddenCode not found or not a valid numeric value
    }

    private Double readMarkFromExcel(Row row) {
        Cell cell = row.getCell(1); // Assuming the mark is in the second column
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        return null; // Mark not found or not a valid numeric value
    }

}