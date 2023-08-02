package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleRequestDTO;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleUpdateDTO;
import com.bjit.finalproject.TMS.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping("/create-schedule")
    public ResponseEntity<Object> createSchedules(@RequestBody ScheduleRequestDTO scheduleRequestDTO){
        return new ResponseEntity<>(scheduleService.createSchedules(scheduleRequestDTO), HttpStatus.OK);
    }
    @GetMapping("/get-schedules")
    public ResponseEntity<Object> getAllSchedules(){
        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }
    @PutMapping("/update-schedule/{scheduleId}")
    public ResponseEntity<Object> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleUpdateDTO scheduleUpdateDTO){
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, scheduleUpdateDTO), HttpStatus.OK);
    }
    @DeleteMapping("/delete-schedule/{scheduleId}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable Long scheduleId){
        return new ResponseEntity<>(scheduleService.deleteSchedule(scheduleId), HttpStatus.OK);
    }
    @GetMapping("/get-schedules-by-batch/{batchName}")
    public ResponseEntity<Object> getBatchSchedules(@PathVariable String batchName){
        return new ResponseEntity<>(scheduleService.getBatchSchedules(batchName), HttpStatus.OK);
    }
    @GetMapping("/get-schedules-by-trainer")
    public ResponseEntity<Object> getTrainerSchedules(){
        return new ResponseEntity<>(scheduleService.getTrainerSchedules(), HttpStatus.OK);
    }
    @GetMapping("/get-schedules-by-trainee")
    public ResponseEntity<Object> getTraineeSchedule(){
        return new ResponseEntity<>(scheduleService.getTraineeSchedule(),HttpStatus.OK);
    }
    @GetMapping("/get-schedule-courses/{courseName}")
    public ResponseEntity<Object> getScheduledCourses(@PathVariable String courseName){
        return new ResponseEntity<>(scheduleService.getScheduledCourses(courseName), HttpStatus.OK);
    }


}
