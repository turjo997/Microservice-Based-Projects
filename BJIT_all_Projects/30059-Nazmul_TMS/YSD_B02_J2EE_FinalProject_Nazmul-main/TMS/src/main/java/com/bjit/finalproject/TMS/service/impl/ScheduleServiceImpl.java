package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleBatchResponse;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleRequestDTO;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleResponseDTO;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleUpdateDTO;
import com.bjit.finalproject.TMS.exception.ScheduleException;
import com.bjit.finalproject.TMS.exception.NameNotFoundException;
import com.bjit.finalproject.TMS.model.*;
import com.bjit.finalproject.TMS.repository.*;
import com.bjit.finalproject.TMS.service.ScheduleService;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.DateFormatterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepo;
    private final TrainerRepository trainerRepo;
    private final CourseRepository courseRepo;
    private final BatchRepository batchRepo;
    private final BatchTraineeRepository btRepository;
    private final DateFormatterService dateFormatterService;
    private final UserService userService;
    @Override
    @Transactional
    public ScheduleResponseDTO createSchedules(ScheduleRequestDTO scheduleRequestDTO) {
        Schedule toBeSavedschedule = convertToSchedule(scheduleRequestDTO);
        Schedule schedule = scheduleRepo.save(toBeSavedschedule);
        return ScheduleResponseDTO.builder()
                .scheduleName(schedule.getScheduleName())
                .courseTitle(schedule.getCourse().getCourseName())
                .batchName(schedule.getBatch().stream().map(s -> s.getBatchName()).collect(Collectors.toList()))
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .trainerEmail(schedule.getTrainer().getEmail())
                .build();
    }

    @Override
    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> requiredSchedules = scheduleRepo.findAll();
        List<ScheduleResponseDTO> responses = new ArrayList<>();
        for(Schedule s:requiredSchedules){
            ScheduleResponseDTO response = ScheduleResponseDTO.builder()
                    .id(s.getScheduleId())
                    .scheduleName(s.getScheduleName())
                    .courseTitle(s.getCourse().getCourseName())
                    .batchName(s.getBatch().stream().map(sc -> sc.getBatchName()).collect(Collectors.toList()))
                    .startTime(dateFormatterService.formatDateForClient(s.getStartTime()))
                    .endTime(dateFormatterService.formatDateForClient(s.getEndTime()))
                    .trainerEmail(s.getTrainer().getEmail())
                    .build();
            responses.add(response);
        }
        return responses;
    }
    @Override
    @Transactional
    public String updateSchedule(Long scheduleId, ScheduleUpdateDTO scheduleUpdateDTO){
        Schedule schedule = scheduleRepo.findById(scheduleId)
                                        .orElseThrow(()->{throw new ScheduleException("no schedule by this id");});
        schedule.setScheduleName(scheduleUpdateDTO.getTitle());
        schedule.setTrainer(trainerRepo.findByEmail(scheduleUpdateDTO.getTrainerEmail())
                                        .orElseThrow(()->{throw new NameNotFoundException("No trainer by this name");}));
        schedule.setCourse(courseRepo.findByCourseName(scheduleUpdateDTO.getCourseTitle())
                                      .orElseThrow(()->{throw new NameNotFoundException("No course by this name");}));
        List<Batch> batches = scheduleUpdateDTO.getBatchName().stream()
                               .map(batchName -> batchRepo.findByBatchName(batchName)
                                                .orElseThrow(()->{throw new NameNotFoundException("No batch by this name");}))
                .collect(Collectors.toList());
        schedule.setBatch(batches);
        scheduleRepo.saveAndFlush(schedule);
        return "Schedule is updated";
    }
    @Override
    @Transactional
    public String deleteSchedule(Long scheduleId){
        Schedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException("Schedule not found"));

        // Manually delete the related batches from the schedules_batch table
        schedule.getBatch().clear(); // Or loop through and remove each batch individually

        scheduleRepo.delete(schedule);
        return "Schedule is deleted";
    }
    @Override
    public List<ScheduleBatchResponse> getBatchSchedules(String batchName) {
        Batch batch = batchRepo.findByBatchName(batchName)
                                .orElseThrow(()->{throw new NameNotFoundException("No such batch");});
        List<Schedule> schedules = scheduleRepo.findAllScheduleByBatch(batch);
        List<ScheduleBatchResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            String startTime = dateFormatterService.formatDateForClient(schedule.getStartTime());
            String endTime = dateFormatterService.formatDateForClient(schedule.getEndTime());
            ScheduleBatchResponse response = ScheduleBatchResponse.builder()
                                            .id(schedule.getScheduleId())
                                            .scheduleName(schedule.getScheduleName())
                                            .trainerEmail(schedule.getTrainer().getEmail())
                                            .courseTitle(schedule.getCourse().getCourseName())
                                            .batchName(schedule.getBatch()
                                                .stream().map(s->s.getBatchName()+" ").collect(Collectors.toList()))
                                            .startTime(startTime)
                                            .endTime(endTime)
                                            .build();
            responses.add(response);
        }
        return responses;
    }
    @Override
    public List<ScheduleBatchResponse> getTrainerSchedules() {
        String email = userService.loggedInUserEmail();
        String role = userService.getUserRole();
        if(!role.equals("TRAINER")){
            throw new ScheduleException("Cannot get the schedules");
        }
        TrainerModel trainer = trainerRepo.findByEmail(email)
                                            .orElseThrow(()->{throw new NameNotFoundException("No trainer by this name");});

//        TrainerModel trainer = requiredTrainer.get();
        List<Schedule> schedules = scheduleRepo.findAllScheduleByTrainer(trainer);
        List<ScheduleBatchResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            String startTime = dateFormatterService.formatDateForClient(schedule.getStartTime());
            String endTime = dateFormatterService.formatDateForClient(schedule.getEndTime());
            ScheduleBatchResponse response = ScheduleBatchResponse.builder()
                                            .id(schedule.getScheduleId())
                                            .scheduleName(schedule.getScheduleName())
                                            .courseTitle(schedule.getCourse().getCourseName())
                                            .batchName(schedule.getBatch()
                                                        .stream().map(s->s.getBatchName()).collect(Collectors.toList()))
                                            .startTime(startTime)
                                            .endTime(endTime)
                                            .build();
            responses.add(response);
        }
        return responses;
    }
    @Override
    public List<ScheduleBatchResponse> getTraineeSchedule() {
        String email = userService.loggedInUserEmail();
        String role = userService.getUserRole();
        if(!role.equals("TRAINEE")){
            throw new ScheduleException("Cannot get the schedules");
        }
        Optional<BatchTrainees> traineeBatch = btRepository.findByTraineeEmail(email);
        if(!traineeBatch.isPresent()){
            throw new NameNotFoundException("No trainee by this name");
        }
        BatchTrainees bt = traineeBatch.get();
        String batchName = bt.getBatchName();
        return getBatchSchedules(batchName);
    }
    @Override
    public List<ScheduleResponseDTO> getScheduledCourses(String courseName) {
        Optional<Course> requiredCourse = courseRepo.findByCourseName(courseName);
        if(!requiredCourse.isPresent()){
            throw new NameNotFoundException("No such course by this name");
        }
        Course course = requiredCourse.get();
        List<Schedule> courseSchedules = scheduleRepo.findAllScheduleByCourse(course);
        List<ScheduleResponseDTO> responses = courseSchedules.stream()
                                                              .map(s->ScheduleResponseDTO
                                                                      .builder()
                                                                      .scheduleName(s.getScheduleName())
                                                                      .courseTitle(s.getCourse().getCourseName())
                                                                      .startTime(s.getStartTime())
                                                                      .endTime(s.getEndTime())
                                                                      .trainerEmail(s.getTrainer().getEmail())
                                                                      .batchName(s.getBatch().stream().map(b->b.getBatchName())
                                                                      .collect(Collectors.toList()))
                                                              .build()).collect(Collectors.toList());
        return responses;
    }
    @Override
    public List<ScheduleBatchResponse> getScheduleTrainerHelper(String email){
        TrainerModel trainer = trainerRepo.findByEmail(email)
                                .orElseThrow(()->{throw new NameNotFoundException("No trainer by this name");} );
        List<Schedule> schedules = scheduleRepo.findAllScheduleByTrainer(trainer);
        List<ScheduleBatchResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            String startTime = dateFormatterService.formatDateForClient(schedule.getStartTime());
            String endTime = dateFormatterService.formatDateForClient(schedule.getEndTime());
            ScheduleBatchResponse response = ScheduleBatchResponse.builder()
                    .id(schedule.getScheduleId())
                    .scheduleName(schedule.getScheduleName())
                    .courseTitle(schedule.getCourse().getCourseName())
                    .batchName(schedule.getBatch()
                            .stream().map(s->s.getBatchName()).collect(Collectors.toList()))
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            responses.add(response);
        }
        return responses;
    }
    @Transactional
    //Taking the schedule request from controller and doing the calculation to save in database
    public Schedule convertToSchedule(ScheduleRequestDTO request){
        if(request.getTrainerEmail().isEmpty() || request.getBatchName().isEmpty() ||request.getStartTime().isEmpty()
                ||request.getEndTime().isEmpty()){
            throw new ScheduleException("Fields cannot be empty");
        }
        Optional<TrainerModel> trainerModel = trainerRepo.findByEmail(request.getTrainerEmail());
        Optional<Course> courseModel = courseRepo.findByCourseName(request.getCourseTitle());
        List<String> requestedBatches = request.getBatchName();
        List<Batch> neededBatches = requestedBatches.stream().map(b->batchRepo.findByBatchName(b).get()).collect(Collectors.toList());
        Batch queryBatch = new Batch();
        for(Batch batch:neededBatches){
            System.out.println(batch.getBatchName());
            System.out.println(requestedBatches.stream().toString());
            if(batch.getBatchName()!=null && requestedBatches.stream().anyMatch(b->b.equals(batch.getBatchName()))){
                queryBatch = batch;
                break;
            }
        }

        if(!trainerModel.isPresent()){
            throw new NameNotFoundException("No trainer by this name");
        }
        TrainerModel trainer = trainerModel.get();
        if(!courseModel.isPresent()){
            throw new NameNotFoundException("No course by this name");
        }
        Course course = courseModel.get();

        //checking if trainer schedule clashes with the requested schedule
        List<Schedule> trainerSchedule = scheduleRepo.findAllScheduleByTrainer(trainer);
        boolean trainerScheduleOverlap = scheduleClashChecker(request, trainerSchedule);

        //checking batches schedules if there is any clash
        List<Schedule> batchSchedules = scheduleRepo.findAllScheduleByBatch(queryBatch);
        boolean isOverlappingSchedule = scheduleClashChecker(request, batchSchedules);
        if(trainerScheduleOverlap || isOverlappingSchedule){
            throw new ScheduleException("Schedule overlapping");
        }
        System.out.println(request.getStartTime());
        System.out.println(request.getEndTime());
        // Send a formatted time and date from frontend in format "yyyy-MM-dd HH:mm:ss"
        return Schedule.builder()
                .scheduleName(request.getScheduleName())
                .course(course)
                .trainer(trainer)
                .batch(neededBatches)
                .startTime(dateFormatterService.formatDateForDatabase(request.getStartTime()))
                .endTime(dateFormatterService.formatDateForDatabase(request.getEndTime()))
                .build();
    }
    private boolean scheduleClashChecker(ScheduleRequestDTO request, List<Schedule> schedule){
        boolean isInValid = false;
        for(Schedule s:schedule){
            String requestStartTime = request.getStartTime();
            String requestEndTime = request.getEndTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String prevStartTime = s.getStartTime();
            String prevEndTime = s.getEndTime();

            try{
                Date requestSTime = formatter.parse(dateFormatterService.formatDateForDatabase(requestStartTime));
                Date requestETime = formatter.parse(dateFormatterService.formatDateForDatabase(requestEndTime));
                Date pStartTime = formatter.parse(prevStartTime);
                Date pEndTime = formatter.parse(prevEndTime);

                boolean isOverlap = (pStartTime.before(requestETime) && requestSTime.before(pEndTime))
                                    ||(pStartTime.after(requestETime) && requestSTime.after(pEndTime));

                if(isOverlap){
                    isInValid = isOverlap;
                    return isInValid;
                }
                isInValid = isOverlap;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return isInValid;
    }

}
