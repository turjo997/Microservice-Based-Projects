package com.example.tss.service.impl;

import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.dto.ScreeningRoundMarkDto;
import com.example.tss.entity.*;
import com.example.tss.exception.ApplicationApprovingFailedException;
import com.example.tss.exception.ApplicationPlacingFailedException;
import com.example.tss.exception.RoundCreationException;
import com.example.tss.model.ApplicationResponseModel;
import com.example.tss.model.ScreeningRoundResponseModel;
import com.example.tss.repository.*;
import com.example.tss.service.AdmitCardService;
import com.example.tss.service.EmailService;
import com.example.tss.service.RoundService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService {
    private final ScreeningRoundRepository screeningRoundRepository;
    private final ApplicationRepository applicationRepository;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningMarksRepository screeningMarksRepository;
    private final CircularRepository circularRepository;
    private final AdmitCardService admitCardService;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> getAllRoundsUnderCircular(Long circularId) {
        ModelMapper modelMapper = new ModelMapper();
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circularId).orElseThrow();

        List<ScreeningRoundDto> roundDtos = screeningRoundRepository.findByCircularId(circularId).stream()
                .map(screeningRound -> modelMapper
                        .map(screeningRound, ScreeningRoundDto.class))
                .toList();
        ScreeningRoundResponseModel screeningRoundResponseModel = ScreeningRoundResponseModel.builder()
                .count(roundDtos.size())
                .currentRoundSerialNo(screeningRoundMeta.getCurrentRound().getSerialNo())
                .rounds(roundDtos)
                .build();
        return ResponseEntity.ok(screeningRoundResponseModel);
    }

    @Override
    public ResponseEntity<?> getRoundByIdUnderCircular(Long circularId, Long roundId) {
        ModelMapper modelMapper = new ModelMapper();
        ScreeningRound screeningRound = screeningRoundRepository.findByIdAndCircularId(roundId, circularId)
                .orElseThrow();
        ScreeningRoundDto screeningRoundDto = modelMapper.map(screeningRound, ScreeningRoundDto.class);
        return ResponseEntity.ok(screeningRoundDto);
    }


    @Override
    @Transactional
    public ResponseEntity<?> storeRound(Long circularId, ScreeningRoundDto screeningRoundDto) {
        Circular circular = circularRepository.findById(circularId)
                .orElseThrow(() -> new RoundCreationException("Circular doesn't exists"));
        int roundPos = screeningRoundDto.getSerialNo();
        int maxPos = 0;
        List<ScreeningRound> rounds = screeningRoundRepository.findByCircularId(circular.getId());
        for (ScreeningRound round : rounds) {
            int curPos = round.getSerialNo();
            if (curPos >= roundPos) {
                round.setSerialNo(curPos + 1);
            }
            if (maxPos < curPos) {
                maxPos = curPos;
            }
        }
        ScreeningRound newScreeningRound = ScreeningRound.builder()
                .circular(circular)
                .title(screeningRoundDto.getTitle())
                .description(screeningRoundDto.getDescription())
                .maxMark(screeningRoundDto.getMaxMark())
                .serialNo(roundPos)
                .passMark(screeningRoundDto.getPassMark())
                .requiredAdmitCard(screeningRoundDto.getRequiredAdmitCard())
                .location(screeningRoundDto.getExamLocation())
                .examTime(screeningRoundDto.getExamTime())
                .build();
        if ((maxPos + 1) == roundPos) {
            screeningRoundRepository.save(newScreeningRound);
        } else {
            rounds.add(newScreeningRound);
            screeningRoundRepository.saveAll(rounds);
        }
        return ResponseEntity.ok(screeningRoundDto);
    }

    @Override
    public ResponseEntity<?> saveCandidateMark(Long circularId, Long roundId, Long candidateId, Float mark) {
        Application application = applicationRepository.findById(candidateId).orElseThrow();
        ScreeningRound round = screeningRoundRepository.findById(roundId).orElseThrow();
        ScreeningRound lastScreeningRound = screeningRoundRepository.findByCircularIdAndSerialNo(circularId, round.getSerialNo() + 1).orElseThrow();
        Long applicationId = application.getId();
        Optional<ScreeningRoundMark> screeningRoundMarkOp = screeningMarksRepository.findByApplicationIdAndRoundId(applicationId, roundId);
        if (screeningRoundMarkOp.isEmpty()) {
            ScreeningRoundMark screeningRoundMark = ScreeningRoundMark.builder()
                    .application(application)
                    .round(round)
                    .mark(mark.doubleValue())
                    .build();
            screeningMarksRepository.save(screeningRoundMark);
        } else {
            ScreeningRoundMark screeningRoundMark = screeningRoundMarkOp.get();
            screeningRoundMark.setMark(mark.doubleValue());
            screeningMarksRepository.save(screeningRoundMark);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRound(Long circularId, Long roundId, ScreeningRoundDto screeningRoundDto) {
        Circular circular = circularRepository.findById(circularId)
                .orElseThrow(() -> new RoundCreationException("Circular doesn't exists"));
        ScreeningRound screeningRound = screeningRoundRepository.findById(roundId)
                .orElseThrow(() -> new RoundCreationException("Round doesn't exists"));
        int roundPos = screeningRoundDto.getSerialNo();

        int maxPos = 0;
        List<ScreeningRound> rounds = screeningRoundRepository.findByCircularId(circular.getId());
        for (ScreeningRound round : rounds) {
            int curPos = round.getSerialNo();
            if (curPos >= roundPos) {
                round.setSerialNo(curPos + 1);
            }
            if (maxPos < curPos) {
                maxPos = curPos;
            }
        }
        screeningRound.setCircular(circular);
        screeningRound.setTitle(screeningRoundDto.getTitle());
        screeningRound.setDescription(screeningRoundDto.getDescription());
        screeningRound.setSerialNo(maxPos + 1);
        screeningRound.setMaxMark(screeningRoundDto.getMaxMark());
        screeningRound.setPassMark(screeningRoundDto.getPassMark());
        screeningRound.setLocation(screeningRoundDto.getExamLocation());
        screeningRound.setExamTime(screeningRoundDto.getExamTime());
        return ResponseEntity.ok(screeningRoundDto);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteRoundByIdUnderCircular(Long circularId, Long roundId) {
        screeningRoundRepository.findByIdAndCircularId(roundId, circularId).orElseThrow();
        screeningRoundRepository.deleteByIdAndCircularId(roundId, circularId);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> approveApplicant(Circular circular, Long applicationId) {
        Application application = applicationRepository.findByIdAndCircularId(applicationId, circular.getId())
                .orElseThrow(ApplicationApprovingFailedException::new);
        ApplicantProfile applicant = application.getApplicant();
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circular.getId())
                .orElseThrow(ApplicationApprovingFailedException::new);
        ScreeningRound currentRound = screeningRoundMeta.getCurrentRound();
        screeningMarksRepository.findByApplicationIdAndRoundId(applicationId, currentRound.getId()).orElseThrow(ApplicationApprovingFailedException::new);
        ScreeningRound applicationCurrentRound = application.getCurrentRound();
        ScreeningRound circularCurrentRound = screeningRoundMeta.getCurrentRound();
        if (circularCurrentRound.getSerialNo().intValue() != applicationCurrentRound.getSerialNo().intValue()) {
            System.out.println("here");
            throw new ApplicationApprovingFailedException();
        }
//        if(circularCurrentRound.getExamTime().before(new Date(System.currentTimeMillis()))){
//            return ResponseEntity.badRequest().build();
//        }
        ScreeningRound screeningRound = screeningRoundRepository.findByCircularIdAndSerialNo(circular.getId(),
                circularCurrentRound.getSerialNo() + 1).orElseThrow();
        application.setCurrentRound(screeningRound);

        if (screeningRound.getRequiredAdmitCard() == null) {
            Application savedApplication = applicationRepository.save(application);
            User user = applicant.getUser();
            String userEmail = user.getEmail();
            emailService.sendInvitationEmail(userEmail, application);
            return ResponseEntity.ok().build();
        }
        if (screeningRound.getRequiredAdmitCard() && admitCardService.generateAdmitCard(application, screeningRound, circular)) {
            Application savedApplication = applicationRepository.save(application);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> endRound(Long circularId) {
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circularId).orElseThrow();
        ScreeningRound currentRound = screeningRoundMeta.getCurrentRound();
        if (currentRound != null) {
            Integer serialNo = currentRound.getSerialNo();
            System.out.println("serial" + serialNo);
            ScreeningRound screeningRound = screeningRoundRepository.findByCircularIdAndSerialNo(circularId, serialNo + 1).orElseThrow();
            System.out.println("serial new" + screeningRound.getSerialNo());
            screeningRoundMeta.setCurrentRound(screeningRound);
            ScreeningRoundMeta saved = screeningRoundMetaRepository.save(screeningRoundMeta);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> getAllCandidatesUnderRoundUnderCircular(Long circularId, Long roundId) {
        List<Application> applications = applicationRepository.findByCircularIdAndCurrentRoundId(circularId, roundId);
        List<ApplicationResponseModel> applicationResponseModels = applications.stream()
                .map(application -> {
                    List<ScreeningRoundMarkDto> screeningRoundMarkDto = screeningMarksRepository.findByApplicationId(application.getId()).stream()
                            .map(screeningRoundMark -> ScreeningRoundMarkDto.builder()
                                    .roundId(screeningRoundMark.getRound().getId())
                                    .title(screeningRoundMark.getRound().getTitle())
                                    .mark(screeningRoundMark.getMark())
                                    .build()).toList();

                    return ApplicationResponseModel.builder()
                            .id(application.getId())
                            .name(application.getFirstName() + " " + application.getLastName())
                            .cgpa(application.getCgpa())
                            .dateOfBirth(application.getDateOfBirth())
                            .gender(application.getGender().name())
                            .degreeName(application.getDegreeName())
                            .passingYear(application.getPassingYear())
                            .institutionName(application.getInstitutionName())
                            .resumeId(application.getResume().getId())
                            .profileImageId(application.getProfileImage().getId())
                            .phone(application.getPhone())
                            .email(application.getEmail())
                            .roundMarks(screeningRoundMarkDto)
                            .build();
                }).toList();
        return ResponseEntity.ok(applicationResponseModels);
    }

}
