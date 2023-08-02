package com.bjit.tss.service.impl;

import com.bjit.tss.entity.*;
import com.bjit.tss.enums.Round;
import com.bjit.tss.exception.EvaluationException;
import com.bjit.tss.exception.HiddenCodeException;
import com.bjit.tss.exception.UserException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.mapper.EvaluatorMapper;
import com.bjit.tss.model.request.*;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.response.EvaluatorAssignmentResponse;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.*;
import com.bjit.tss.service.EmailService;
import com.bjit.tss.service.EvaluationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluatorRepository evaluatorRepository;
    private final CandidateRepository candidateRepository;
    private final HiddenCodeRepository hiddenCodeRepository;
    private final DataStorageRepository dataStorageRepository;
    private final WrittenMarksRepository writtenMarksRepository;
    private final WrittenQuestionMarksRepository writtenQuestionMarksRepository;
    private final QuestionMarksRepository questionMarksRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> assignAnswerSheet(AssignAnswerSheetRequest request) {
        Optional<EvaluatorInfo> evaluatorInfo = evaluatorRepository.findById(request.getEvaluatorId());
        if (evaluatorInfo.isEmpty()) {
            throw new UserException("Evaluator does not exist");
        }
        List<CandidateMarks> candidateMarks = candidateRepository.findAllById(request.getCandidateIds());
        if (candidateMarks.size() == 0) {
            throw new UserException("Candidates does not exist");
        }
        List<HiddenCodeInfo> result = candidateMarks.stream().map(candidate -> {
            HiddenCodeInfo hiddenCode = null;
            WrittenMarks writtenMarks;
            if (candidate.getWrittenMarks() == null || candidate.getWrittenMarks().getWrittenMarkId() == null) {
                writtenMarks = WrittenMarks.builder()
                        .evaluatorInfo(evaluatorInfo.get())
                        .build();
            } else {
                writtenMarks = candidate.getWrittenMarks();
                writtenMarks.setEvaluatorInfo(evaluatorInfo.get());
            }
            candidate.setWrittenMarks(writtenMarks);

            Optional<HiddenCodeInfo> codeInfo = hiddenCodeRepository.findByCandidateMarksCandidateId(candidate.getCandidateId());
            if (codeInfo.isPresent()) {
                hiddenCode = HiddenCodeInfo.builder()
                        .hiddenCode(codeInfo.get().getHiddenCode())
                        .candidateMarks(candidate)
                        .build();
            } else {
                hiddenCode = HiddenCodeInfo.builder()
                        .candidateMarks(candidate)
                        .build();
            }

            return hiddenCode;
        }).toList();

        List<HiddenCodeInfo> saved = hiddenCodeRepository.saveAll(result);
        List<EvaluatorAssignmentResponse> assignmentResponseList = saved.stream().map(EvaluatorMapper::mapToEvaluatorAssignmentResponse).toList();

        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(assignmentResponseList.size())
                .listResponse(assignmentResponseList)
                .build();
        System.out.println("Successfully assigned to " + evaluatorInfo.get().getName());
        return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Successfully assigned to " + evaluatorInfo.get().getName());
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> uploadWrittenMark(UploadWrittenMarkRequest request) {
        Optional<HiddenCodeInfo> hiddenCodeInfo = hiddenCodeRepository.findById(request.getHiddenCode());
        if (hiddenCodeInfo.isEmpty() || hiddenCodeInfo.get().getCandidateMarks().getWrittenMarks().getEvaluatorInfo() == null) {
            throw new HiddenCodeException("Invalid hidden code");
        }
        LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(hiddenCodeInfo.get().getCandidateMarks().getWrittenMarks().getEvaluatorInfo().getEvaluatorId(), loginInfo.getEvaluatorInfo().getEvaluatorId())) {
            throw new HiddenCodeException("Invalid hidden code");
        }

        Optional<DataStorage> dataStorage = dataStorageRepository.findByDataKey("WrittenQuestionNumber");
        if (dataStorage.isEmpty()) {
            throw new EvaluationException("Ask admin to set the number of written question");
        }

        if (Integer.parseInt(dataStorage.get().getDataValue()) != (Integer) request.getMarks().size()) {
            throw new EvaluationException("Number of written questions are : " + dataStorage.get().getDataValue());
        }

        WrittenMarks writtenMarks = hiddenCodeInfo.get().getCandidateMarks().getWrittenMarks();
        List<Long> ids = request.getMarks().stream().map(iterate -> writtenMarks.getWrittenMarkId()).toList();

        List<Long> writtenQuestionIds = null;
        try {
            writtenQuestionIds = IntStream.range(0, ids.size())
                    .mapToObj(i -> writtenMarks.getWrittenQuestionMarks().get(i).getWrittenQuestionId()).toList();
        } catch (Exception ignored) {
        }

        AtomicReference<Integer> questionNo = new AtomicReference<>(0);
        AtomicReference<Float> totalMark = new AtomicReference<>(0.0f);

        List<WrittenQuestionMarks> writtenQuestionMarks = null;
        writtenQuestionMarks = request.getMarks().stream().map(question -> {
            questionNo.set(questionNo.get() + 1);
            if (question < 0) {
                throw new EvaluationException("Mark cannot be negative");
            }
            totalMark.set(totalMark.get() + question);
            return WrittenQuestionMarks.builder()
                    .writtenQuestionMark(question)
                    .questionNo(Integer.parseInt(String.valueOf(questionNo)))
                    .build();
        }).toList();
        questionNo.set(0);

        List<WrittenQuestionMarks> writtenQuestionMarksList = null;
        if (writtenQuestionIds != null) {
            if (writtenMarks.getWrittenQuestionMarks().size() == writtenQuestionIds.size()) {
                List<Long> finalWrittenQuestionIds = writtenQuestionIds;
                List<WrittenQuestionMarks> finalWrittenQuestionMarks = writtenQuestionMarks;
                writtenQuestionMarksList = IntStream.range(0, writtenQuestionMarks.size())
                        .mapToObj(i -> {
                            finalWrittenQuestionMarks.get(i).setWrittenQuestionId(finalWrittenQuestionIds.get(i));
                            return finalWrittenQuestionMarks.get(i);
                        }).toList();
            }
        }

        Optional<DataStorage> maximumMark = dataStorageRepository.findByDataKey("TotalMarkWritten");
        Optional<DataStorage> passingMark = dataStorageRepository.findByDataKey("PassingMarkWritten");
        if (maximumMark.isEmpty()) {
            throw new EvaluationException("Ask admin to set total mark of written exam.");
        }

        if (passingMark.isEmpty()) {
            throw new EvaluationException("Ask admin to set passing mark of written exam");
        }

        if (totalMark.get() > Float.parseFloat(maximumMark.get().getDataValue())) {
            throw new EvaluationException("Total written mark cannot be greater than " + maximumMark.get().getDataValue());
        }

        boolean isPassed = totalMark.get() >= Float.parseFloat(passingMark.get().getDataValue());

        List<WrittenQuestionMarks> savedQuestionMarks = null;
        if (writtenQuestionIds != null) {
            if (writtenMarks.getWrittenQuestionMarks().size() == writtenQuestionIds.size()) {
                assert writtenQuestionMarksList != null;
                savedQuestionMarks = writtenQuestionMarksRepository.saveAll(writtenQuestionMarksList);
            } else {
                savedQuestionMarks = writtenQuestionMarksRepository.saveAll(writtenQuestionMarks);
            }
        } else {
            savedQuestionMarks = writtenQuestionMarksRepository.saveAll(writtenQuestionMarks);
        }

        writtenMarks.setWrittenMark(totalMark.get());
        writtenMarks.setWrittenQuestionMarks(savedQuestionMarks);
        writtenMarks.setPassed(isPassed);
        if (isPassed){
            String emailBody = hiddenCodeInfo.get().getCandidateMarks().getExamineeInfo().getCourseInfo().getWrittenPassedDashboardMessage();
            List<String> to = new ArrayList<String>();
            to.add(hiddenCodeInfo.get().getCandidateMarks().getExamineeInfo().getUserInfo().getEmail());
            String[] toEmail = to.toArray(new String[0]);
            String emailSubject = "Written Exam Passed";

            EmailRequest emailRequest = EmailRequest.builder()
                    .to(toEmail)
                    .body(emailBody)
                    .subject(emailSubject)
                    .build();
            ResponseEntity<ApiResponse<?>> emailResponse = emailService.sendEmail(emailRequest);

            System.out.println(hiddenCodeInfo.get().getCandidateMarks().getExamineeInfo().getUserInfo().getFirstName()+ " was approved by admin. A email was sent to : "+hiddenCodeInfo.get().getCandidateMarks().getExamineeInfo().getUserInfo().getEmail());
            System.out.println("The message was : "+emailBody );
        }

        WrittenMarks savedMarks = writtenMarksRepository.save(writtenMarks);
        return ApiResponseMapper.mapToResponseEntityOK(savedMarks, "Mark updated successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> uploadAptitudeMark(UploadMarkRequest request) {
        Optional<CandidateMarks> candidateMarks = candidateRepository.findById(request.getCandidateId());
        if (candidateMarks.isEmpty()) {
            throw new UserException("Candidate id is invalid");
        }

        if (!candidateMarks.get().getWrittenMarks().getPassed()) {
            throw new EvaluationException("Invalid Request");
        }

        Optional<DataStorage> aptitudeQuestionNumber = dataStorageRepository.findByDataKey("AptitudeQuestionNumber");
        if (aptitudeQuestionNumber.isEmpty()) {
            throw new EvaluationException("Please set the number of questions in aptitude test");

        }

        if (Integer.parseInt(aptitudeQuestionNumber.get().getDataValue()) != (Integer) request.getMarks().size()) {
            throw new EvaluationException("Number of questions in aptitude test is : " + aptitudeQuestionNumber.get().getDataValue());
        }
        AtomicReference<Integer> questionNo = new AtomicReference<>(0);
        AtomicReference<Float> totalMark = new AtomicReference<>(0.0f);
        List<QuestionMarks> questionMarks;
        if (candidateMarks.get().getAptitudeTest().getQuestionMarksList().size() == 0) {
            questionMarks = request.getMarks().stream().map(question -> {
                questionNo.set(questionNo.get() + 1);
                if (question < 0) {
                    throw new EvaluationException("Mark cannot be negative");
                }
                totalMark.set(totalMark.get() + question);

                return QuestionMarks.builder()
                        .questionMark(question)
                        .questionNo(Integer.parseInt(String.valueOf(questionNo)))
                        .build();
            }).toList();
        } else {
            questionMarks = IntStream.range(0, candidateMarks.get().getAptitudeTest().getQuestionMarksList().size())
                    .mapToObj(index -> {
                        QuestionMarks candidate = candidateMarks.get().getAptitudeTest().getQuestionMarksList().get(index);

                        if (request.getMarks().get(index) < 0) {
                            throw new EvaluationException("Mark cannot be negative");
                        }
                        candidate.setQuestionMark(request.getMarks().get(index));
                        totalMark.set(totalMark.get() + request.getMarks().get(index));

                        return candidate;
                    })
                    .toList();
        }
        Optional<DataStorage> maximumMark = dataStorageRepository.findByDataKey("TotalMarkAptitude");
        Optional<DataStorage> passingMark = dataStorageRepository.findByDataKey("PassingMarkAptitude");
        if (maximumMark.isEmpty()) {
            throw new EvaluationException("Please set total mark of aptitude test.");
        }

        if (passingMark.isEmpty()) {
            throw new EvaluationException("Please set passing mark of aptitude test.");
        }

        if (totalMark.get() > Float.parseFloat(maximumMark.get().getDataValue())) {
            throw new EvaluationException("Total aptitude test mark cannot be greater than " + maximumMark.get().getDataValue());
        }

        boolean isPassed = totalMark.get() >= Float.parseFloat(passingMark.get().getDataValue());
        List<QuestionMarks> questionMarksList = questionMarksRepository.saveAll(questionMarks);
        candidateMarks.get().getAptitudeTest().setQuestionMarksList(questionMarksList);
        candidateMarks.get().getAptitudeTest().setRoundMark(totalMark.get());
        candidateMarks.get().getAptitudeTest().setPassed(isPassed);
        candidateMarks.get().getAptitudeTest().setRoundName(Round.APTITUDE);

        if (isPassed){
            String emailBody = candidateMarks.get().getExamineeInfo().getCourseInfo().getAptitudeTestPassedDashboardMessage();
            List<String> to = new ArrayList<String>();
            to.add(candidateMarks.get().getExamineeInfo().getUserInfo().getEmail());
            String[] toEmail = to.toArray(new String[0]);
            String emailSubject = "Aptitude Test Passed";

            EmailRequest emailRequest = EmailRequest.builder()
                    .to(toEmail)
                    .body(emailBody)
                    .subject(emailSubject)
                    .build();
            ResponseEntity<ApiResponse<?>> emailResponse = emailService.sendEmail(emailRequest);

            System.out.println(candidateMarks.get().getExamineeInfo().getUserInfo().getFirstName()+ " was approved by admin. A email was sent to : "+candidateMarks.get().getExamineeInfo().getUserInfo().getEmail());
            System.out.println("The message was : "+emailBody );
        }

        questionNo.set(0);
        totalMark.set(0.0f);
        CandidateMarks saved = candidateRepository.save(candidateMarks.get());
        return ApiResponseMapper.mapToResponseEntityOK(saved, "Aptitude test mark uploaded successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> uploadTechnicalMark(UploadMarkRequest request) {
        Optional<CandidateMarks> candidateMarks = candidateRepository.findById(request.getCandidateId());
        if (candidateMarks.isEmpty()) {
            throw new UserException("Candidate id is invalid");
        }

        if (!candidateMarks.get().getAptitudeTest().getPassed()) {
            throw new EvaluationException("Invalid Request");
        }
        if (!candidateMarks.get().getWrittenMarks().getPassed()) {
            throw new EvaluationException("Invalid Request");
        }

        Optional<DataStorage> technicalQuestionNumber = dataStorageRepository.findByDataKey("TechnicalQuestionNumber");
        if (technicalQuestionNumber.isEmpty()) {
            throw new EvaluationException("Please set the number of questions in technical viva");

        }
        if (Integer.parseInt(technicalQuestionNumber.get().getDataValue()) != (Integer) request.getMarks().size()) {
            throw new EvaluationException("Number of questions in technical viva is : " + technicalQuestionNumber.get().getDataValue());
        }
        AtomicReference<Integer> questionNo = new AtomicReference<>(0);
        AtomicReference<Float> totalMark = new AtomicReference<>(0.0f);
        List<QuestionMarks> questionMarks;
        if (candidateMarks.get().getTechnicalViva().getQuestionMarksList().size() == 0) {
            questionMarks = request.getMarks().stream().map(question -> {
                questionNo.set(questionNo.get() + 1);
                if (question < 0) {
                    throw new EvaluationException("Mark cannot be negative");
                }
                totalMark.set(totalMark.get() + question);

                return QuestionMarks.builder()
                        .questionMark(question)
                        .questionNo(Integer.parseInt(String.valueOf(questionNo)))
                        .build();
            }).toList();
        } else {
            questionMarks = IntStream.range(0, candidateMarks.get().getTechnicalViva().getQuestionMarksList().size())
                    .mapToObj(index -> {
                        QuestionMarks questionMark = candidateMarks.get().getTechnicalViva().getQuestionMarksList().get(index);

                        if (request.getMarks().get(index) < 0) {
                            throw new EvaluationException("Mark cannot be negative");
                        }
                        questionMark.setQuestionMark(request.getMarks().get(index));
                        totalMark.set(totalMark.get() + request.getMarks().get(index));

                        return questionMark;
                    })
                    .toList();
        }
        Optional<DataStorage> maximumMark = dataStorageRepository.findByDataKey("TotalMarkTechnical");
        Optional<DataStorage> passingMark = dataStorageRepository.findByDataKey("PassingMarkTechnical");
        if (maximumMark.isEmpty()) {
            throw new EvaluationException("Please set total mark of technical viva.");
        }

        if (passingMark.isEmpty()) {
            throw new EvaluationException("Please set passing mark of technical viva.");
        }

        if (totalMark.get() > Float.parseFloat(maximumMark.get().getDataValue())) {
            throw new EvaluationException("Total technical viva mark cannot be greater than " + maximumMark.get().getDataValue());
        }

        boolean isPassed = totalMark.get() >= Float.parseFloat(passingMark.get().getDataValue());
        List<QuestionMarks> questionMarksList = questionMarksRepository.saveAll(questionMarks);
        candidateMarks.get().getTechnicalViva().setQuestionMarksList(questionMarksList);
        candidateMarks.get().getTechnicalViva().setRoundMark(totalMark.get());
        candidateMarks.get().getTechnicalViva().setPassed(isPassed);
        candidateMarks.get().getTechnicalViva().setRoundName(Round.TECHNICAL);

        if (isPassed){
            String emailBody = candidateMarks.get().getExamineeInfo().getCourseInfo().getTechnicalVivaPassedDashboardMessage();
            List<String> to = new ArrayList<String>();
            to.add(candidateMarks.get().getExamineeInfo().getUserInfo().getEmail());
            String[] toEmail = to.toArray(new String[0]);
            String emailSubject = "Technical Test Passed";

            EmailRequest emailRequest = EmailRequest.builder()
                    .to(toEmail)
                    .body(emailBody)
                    .subject(emailSubject)
                    .build();
            ResponseEntity<ApiResponse<?>> emailResponse = emailService.sendEmail(emailRequest);

            System.out.println(candidateMarks.get().getExamineeInfo().getUserInfo().getFirstName()+ " was approved by admin. A email was sent to : "+candidateMarks.get().getExamineeInfo().getUserInfo().getEmail());
            System.out.println("The message was : "+emailBody );
        }

        questionNo.set(0);
        totalMark.set(0.0f);
        CandidateMarks saved = candidateRepository.save(candidateMarks.get());
        return ApiResponseMapper.mapToResponseEntityOK(saved, "Technical viva mark uploaded successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> uploadHrVivaMark(UploadMarkRequest request) {
        Optional<CandidateMarks> candidateMarks = candidateRepository.findById(request.getCandidateId());
        if (candidateMarks.isEmpty()) {
            throw new UserException("Candidate id is invalid");
        }

        if (!candidateMarks.get().getAptitudeTest().getPassed()) {
            throw new EvaluationException("Invalid Request");
        }
        if (!candidateMarks.get().getWrittenMarks().getPassed()) {
            throw new EvaluationException("Invalid Request");
        }
        if (!candidateMarks.get().getTechnicalViva().getPassed()) {
            throw new EvaluationException("Invalid Request");
        }

        Optional<DataStorage> hrVivaQuestionNumber = dataStorageRepository.findByDataKey("HrVivaQuestionNumber");
        if (hrVivaQuestionNumber.isEmpty()) {
            throw new EvaluationException("Please set the number of questions in hr viva");
        }

        if (Integer.parseInt(hrVivaQuestionNumber.get().getDataValue()) != (Integer) request.getMarks().size()) {
            throw new EvaluationException("Number of questions in hr viva is : " + hrVivaQuestionNumber.get().getDataValue());
        }
        AtomicReference<Integer> questionNo = new AtomicReference<>(0);
        AtomicReference<Float> totalMark = new AtomicReference<>(0.0f);
        List<QuestionMarks> questionMarks;
        if (candidateMarks.get().getHrViva().getQuestionMarksList().size() == 0) {
            questionMarks = request.getMarks().stream().map(question -> {
                questionNo.set(questionNo.get() + 1);
                if (question < 0) {
                    throw new EvaluationException("Mark cannot be negative");
                }
                totalMark.set(totalMark.get() + question);

                return QuestionMarks.builder()
                        .questionMark(question)
                        .questionNo(Integer.parseInt(String.valueOf(questionNo)))
                        .build();
            }).toList();
        } else {
            questionMarks = IntStream.range(0, candidateMarks.get().getHrViva().getQuestionMarksList().size())
                    .mapToObj(index -> {
                        QuestionMarks questionMark = candidateMarks.get().getHrViva().getQuestionMarksList().get(index);

                        if (request.getMarks().get(index) < 0) {
                            throw new EvaluationException("Mark cannot be negative");
                        }
                        questionMark.setQuestionMark(request.getMarks().get(index));
                        totalMark.set(totalMark.get() + request.getMarks().get(index));

                        return questionMark;
                    })
                    .toList();
        }

        Optional<DataStorage> maximumMark = dataStorageRepository.findByDataKey("TotalMarkHrViva");
        Optional<DataStorage> passingMark = dataStorageRepository.findByDataKey("PassingMarkHrViva");
        if (maximumMark.isEmpty()) {
            throw new EvaluationException("Please set total mark of hr viva.");
        }

        if (passingMark.isEmpty()) {
            throw new EvaluationException("Please set passing mark of hr viva.");
        }

        if (totalMark.get() > Float.parseFloat(maximumMark.get().getDataValue())) {
            throw new EvaluationException("Total hr viva mark cannot be greater than " + maximumMark.get().getDataValue());
        }

        boolean isPassed = totalMark.get() >= Float.parseFloat(passingMark.get().getDataValue());
        List<QuestionMarks> questionMarksList = questionMarksRepository.saveAll(questionMarks);
        candidateMarks.get().getHrViva().setQuestionMarksList(questionMarksList);
        candidateMarks.get().getHrViva().setRoundMark(totalMark.get());
        candidateMarks.get().getHrViva().setPassed(isPassed);
        candidateMarks.get().getHrViva().setRoundName(Round.HR);
        Float fullMark = candidateMarks.get().getWrittenMarks().getWrittenMark() + candidateMarks.get().getAptitudeTest().getRoundMark() + candidateMarks.get().getTechnicalViva().getRoundMark() + candidateMarks.get().getHrViva().getRoundMark();
        candidateMarks.get().setFullMark(fullMark);

        questionNo.set(0);
        totalMark.set(0.0f);
        CandidateMarks saved = candidateRepository.save(candidateMarks.get());

        if(isPassed){
            String emailBody = saved.getExamineeInfo().getCourseInfo().getHrVivaPassedDashboardMessage();
            List<String> to = new ArrayList<String>();
            to.add(saved.getExamineeInfo().getUserInfo().getEmail());
            String[] toEmail = to.toArray(new String[0]);
            String emailSubject = "HR Viva Passed";

            EmailRequest emailRequest = EmailRequest.builder()
                    .to(toEmail)
                    .body(emailBody)
                    .subject(emailSubject)
                    .build();
            ResponseEntity<ApiResponse<?>> emailResponse = emailService.sendEmail(emailRequest);
            System.out.println(saved.getExamineeInfo().getUserInfo().getFirstName()+ " was approved by admin. A email was sent to : "+saved.getExamineeInfo().getUserInfo().getEmail());

        }
        return ApiResponseMapper.mapToResponseEntityOK(saved, "HR viva mark uploaded successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getRoundPassedSpecific(RoundCandidatesRequest request) {

        List<CandidateMarks> candidateMarks = null;

        switch (request.getRoundName()) {
            case "aptitude":
                candidateMarks = candidateRepository.findAllByAptitudeTestPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(true, request.getBatchCode(), true);
                break;

            case "written":
                candidateMarks = candidateRepository.findAllByWrittenMarksPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(true, request.getBatchCode(), true);
                break;

            case "technical":
                candidateMarks = candidateRepository.findAllByTechnicalVivaPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(true, request.getBatchCode(), true);
                break;

            case "hrviva":
                candidateMarks = candidateRepository.findAllByHrVivaPassedAndExamineeInfoCourseInfoBatchCodeAndExamineeInfoCourseInfoIsAvailable(true, request.getBatchCode(), true);
                break;

            default:
                throw new EvaluationException("Invalid Request");
        }

        ListResponse<?> listResponse = ListResponse.builder()
                .listResponse(candidateMarks)
                .dataLength(candidateMarks.size())
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Candidates who have passed " + request.getRoundName() + " round of " + request.getBatchCode() + " batch code.");
    }
}