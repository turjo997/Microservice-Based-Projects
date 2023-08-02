package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.dto.batchDto.BatchDTO;
import com.bjit.finalproject.TMS.dto.batchDto.BatchTraineeDTO;
import com.bjit.finalproject.TMS.dto.batchDto.BatchTraineeDetailsDTO;
import com.bjit.finalproject.TMS.exception.AlreadyExistsException;
import com.bjit.finalproject.TMS.exception.NameNotFoundException;
import com.bjit.finalproject.TMS.exception.BatchDateException;
import com.bjit.finalproject.TMS.exception.IsEmptyException;
import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.BatchTrainees;
import com.bjit.finalproject.TMS.model.UserModel;
import com.bjit.finalproject.TMS.model.classroom.Classroom;
import com.bjit.finalproject.TMS.repository.BatchRepository;
import com.bjit.finalproject.TMS.repository.BatchTraineeRepository;
import com.bjit.finalproject.TMS.repository.ClassroomRepository;
import com.bjit.finalproject.TMS.repository.UserRepository;
import com.bjit.finalproject.TMS.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {
    private final BatchRepository batchRepository;
    private final BatchTraineeRepository btRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classRoomRepo;
    @Override
    public Batch createBatch(BatchDTO batchDTO) {
        if(batchDTO.getBatchName().isEmpty()||batchDTO.getStartTime()==null||batchDTO.getStartTime()==null){
            throw new IsEmptyException("Fields cannot be empty");
        }
        Optional<Batch> batchName = batchRepository.findByBatchName(batchDTO.getBatchName());
        String startTime = formatDateToString(batchDTO.getStartTime());
        String endTime = formatDateToString(batchDTO.getEndTime());
        boolean dateClash = dateClashChecker(startTime, endTime);
        if(batchName.isPresent()){
            throw new AlreadyExistsException("Batch already exists");
        }
        else if(dateClash){
            throw new BatchDateException("Start date should be before end date");
        }
        Batch saveBatch = Batch.builder()
                .batchName(batchDTO.getBatchName().toUpperCase())
                .startTime(startTime)
                .endTime(endTime)
                .build();
        batchRepository.save(saveBatch);
        Classroom classroom = Classroom.builder()
                                        .classroomTitle("Classroom "+batchDTO.getBatchName())
                                        .batch(saveBatch)
                                        .build();
        classRoomRepo.save(classroom);
        return saveBatch;
    }
    @Override
    public List<Batch> getBatches(){
        List<Batch> batches = batchRepository.findAll();
        return batches;
    }
    //Assign trainees to batch
    @Override
    @Transactional
    public List<BatchTrainees> assignTraineesToBatch(List<BatchTraineeDTO> batchTraineeDTOS){
        List<BatchTrainees> batchTrainees = new ArrayList<>();
        for(BatchTraineeDTO batchTrainee: batchTraineeDTOS){
            Optional<UserModel> trainee = userRepository.findByEmail(batchTrainee.getTraineeEmail());
            if(trainee.isPresent()){
                UserModel user = trainee.get();

                if(traineeChecker(user)){
                    Optional<Batch> requiredBatch = batchRepository.findByBatchName(batchTrainee.getBatchName());
                    if(!requiredBatch.isPresent()){
                        throw new NameNotFoundException("No Such batch by the requested name");
                    }
                    BatchTrainees savedBatchTrainees = BatchTrainees.builder()
                                                        .traineeEmail(batchTrainee.getTraineeEmail())
                                                        .batchName(batchTrainee.getBatchName())
                                                        .build();
                    btRepository.save(savedBatchTrainees);
                    batchTrainees.add(savedBatchTrainees);
                }
            }

        }
        return batchTrainees;
    }

    @Override
    public List<BatchTraineeDetailsDTO> getTraineesByBatch(String batchName){
        batchName = batchName.toUpperCase();
        List<BatchTrainees> trainees = btRepository.findAllByBatchName(batchName);
        if(trainees.isEmpty()){
            throw new IsEmptyException("Returned value is empty");
        }
        List<BatchTraineeDetailsDTO> batchTrainees = new ArrayList<>();
        for(BatchTrainees trainee:trainees){
            String email = trainee.getTraineeEmail();
            Optional<UserModel> userOpt = userRepository.findByEmail(email);
            if(!userOpt.isPresent()){
                throw new NameNotFoundException("Trainee is not registered");
            }
            UserModel user = userOpt.get();
            BatchTraineeDetailsDTO requiredTrainee = BatchTraineeDetailsDTO.builder()
                                                    .traineeEmail(user.getEmail())
                                                    .profilePicture(user.getProfilePicture())
                                                    .phoneNo(user.getPhoneNo())
                                                    .batchName(trainee.getBatchName()).build();
            batchTrainees.add(requiredTrainee);
        }
        return batchTrainees;
    }
    private boolean dateClashChecker(String startDate, String endDate){
        boolean isInValid = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date startTime = formatter.parse(startDate);
            Date endTime =  formatter.parse(endDate);
            if(startTime.after(endTime)){
                isInValid = true;
                return isInValid;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isInValid;
    }
    private String formatDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
    private boolean traineeChecker(UserModel user){
        String roleName = user.getRole().getRoleName();
        return roleName.equals("TRAINEE");
    }
}
