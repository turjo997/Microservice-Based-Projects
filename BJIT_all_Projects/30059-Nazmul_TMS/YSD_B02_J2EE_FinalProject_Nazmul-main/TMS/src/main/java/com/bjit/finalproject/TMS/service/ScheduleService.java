package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleBatchResponse;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleRequestDTO;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleResponseDTO;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleUpdateDTO;
import com.bjit.finalproject.TMS.model.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDTO createSchedules(ScheduleRequestDTO scheduleRequestDTO);
    List<ScheduleResponseDTO> getAllSchedules();
    String updateSchedule(Long scheduleId, ScheduleUpdateDTO scheduleUpdateDTO);
    String deleteSchedule(Long scheduleId);
    List<ScheduleBatchResponse> getBatchSchedules(String batchName);
    List<ScheduleBatchResponse> getTrainerSchedules();
    List<ScheduleBatchResponse> getScheduleTrainerHelper(String email);
    List<ScheduleBatchResponse> getTraineeSchedule();
    List<ScheduleResponseDTO> getScheduledCourses(String courseName);
}
