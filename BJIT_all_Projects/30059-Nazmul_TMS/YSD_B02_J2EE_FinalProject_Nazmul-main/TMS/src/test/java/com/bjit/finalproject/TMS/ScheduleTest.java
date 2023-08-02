package com.bjit.finalproject.TMS;

import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleBatchResponse;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleUpdateDTO;
import com.bjit.finalproject.TMS.exception.ScheduleException;
import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.Course;
import com.bjit.finalproject.TMS.model.Schedule;
import com.bjit.finalproject.TMS.model.TrainerModel;
import com.bjit.finalproject.TMS.repository.BatchRepository;
import com.bjit.finalproject.TMS.repository.CourseRepository;
import com.bjit.finalproject.TMS.repository.ScheduleRepository;
import com.bjit.finalproject.TMS.repository.TrainerRepository;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.service.impl.ScheduleServiceImpl;
import com.bjit.finalproject.TMS.utils.DateFormatterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private ScheduleRepository scheduleRepo;

    @Mock
    private TrainerRepository trainerRepo;

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private BatchRepository batchRepo;
    @Mock
    private DateFormatterService dateFormatterService;
    @Mock
    private UserService userService;

    @Test
    public void testUpdateSchedule_Success() {
        // Sample data for testing
        Long scheduleId = 1L;
        ScheduleUpdateDTO scheduleUpdateDTO = new ScheduleUpdateDTO();
        scheduleUpdateDTO.setTitle("New Schedule Title");
        scheduleUpdateDTO.setTrainerEmail("trainer@example.com");
        scheduleUpdateDTO.setCourseTitle("Course Name");
        scheduleUpdateDTO.setBatchName(Arrays.asList("Batch A", "Batch B"));

        // Mocking the Schedule, Trainer, Course, and Batch objects
        Schedule schedule = new Schedule();
        schedule.setScheduleId(scheduleId);

        TrainerModel trainer = new TrainerModel();

        Course course = new Course();

        Batch batchA = new Batch();

        Batch batchB = new Batch();
        // Set necessary batch B properties

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(trainerRepo.findByEmail(scheduleUpdateDTO.getTrainerEmail())).thenReturn(Optional.of(trainer));
        when(courseRepo.findByCourseName(scheduleUpdateDTO.getCourseTitle())).thenReturn(Optional.of(course));
        when(batchRepo.findByBatchName("Batch A")).thenReturn(Optional.of(batchA));
        when(batchRepo.findByBatchName("Batch B")).thenReturn(Optional.of(batchB));

        // Perform the method call
        String result = scheduleService.updateSchedule(scheduleId, scheduleUpdateDTO);

        // Assertions
        assertEquals("Schedule is updated", result);
        assertEquals(scheduleUpdateDTO.getTitle(), schedule.getScheduleName());
        assertEquals(trainer, schedule.getTrainer());
        assertEquals(course, schedule.getCourse());
        assertEquals(Arrays.asList(batchA, batchB), schedule.getBatch());

        // Verify that the repository saveAndFlush method was called once
        verify(scheduleRepo, times(1)).saveAndFlush(schedule);
    }

    @Test
    public void testGetTrainerSchedules_Success() {
        // Mock the logged-in user's email and role
        when(userService.loggedInUserEmail()).thenReturn("trainer@example.com");
        when(userService.getUserRole()).thenReturn("TRAINER");

        // Mock the TrainerModel returned by trainerRepo.findByEmail
        TrainerModel trainer = new TrainerModel();
        trainer.setEmail("trainer@example.com");
        when(trainerRepo.findByEmail("trainer@example.com")).thenReturn(Optional.of(trainer));

        // Mock the list of schedules returned by scheduleRepo.findAllScheduleByTrainer
        Schedule schedule1 = Schedule.builder()
                .scheduleId(1L)
                .scheduleName("Schedule 1")
                .startTime("2023-07-25T10:07")
                .endTime("2023-07-25T12:07")
                .course(Course.builder().courseName("Course 1").build())
                .batch(Arrays.asList(Batch.builder().batchName("Batch 1").build()))
                .trainer(trainer)
                .build();

        Schedule schedule2 = Schedule.builder()
                .scheduleId(2L)
                .scheduleName("Schedule 2")
                .startTime("2023-07-26T09:30")
                .endTime("2023-07-26T11:30")
                .course(Course.builder().courseName("Course 2").build())
                .batch(Arrays.asList(Batch.builder().batchName("Batch 2").build()))
                .trainer(trainer)
                .build();

        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
        when(scheduleRepo.findAllScheduleByTrainer(trainer)).thenReturn(schedules);

        // Mock the DateFormatterService behavior
        when(dateFormatterService.formatDateForClient(any()))
                .thenReturn("formattedDateTime");

        // Perform the method call
        List<ScheduleBatchResponse> responses = scheduleService.getTrainerSchedules();

        // Assertions
        assertNotNull(responses);
        assertEquals(2, responses.size());

        // Verify the properties of the ScheduleBatchResponse objects
        ScheduleBatchResponse response1 = responses.get(0);
        assertEquals(1L, response1.getId());
        assertEquals("Schedule 1", response1.getScheduleName());
        assertEquals("formattedDateTime", response1.getStartTime());
        assertEquals("formattedDateTime", response1.getEndTime());
        assertEquals("Course 1", response1.getCourseTitle());
//        assertEquals("trainer@example.com", response1.getTrainerEmail());
        assertEquals(Arrays.asList("Batch 1"), response1.getBatchName());

        ScheduleBatchResponse response2 = responses.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("Schedule 2", response2.getScheduleName());
        assertEquals("formattedDateTime", response2.getStartTime());
        assertEquals("formattedDateTime", response2.getEndTime());
        assertEquals("Course 2", response2.getCourseTitle());
//        assertEquals("trainer@example.com", response2.getTrainerEmail());
        assertEquals(Arrays.asList("Batch 2"), response2.getBatchName());

        // Verify that the required methods were called
        verify(userService, times(1)).loggedInUserEmail();
        verify(userService, times(1)).getUserRole();
//        verify(trainerRepo, times(1)).findByEmail("trainer@example.com");
        verify(scheduleRepo, times(1)).findAllScheduleByTrainer(trainer);
        verify(dateFormatterService, times(schedules.size() * 2)).formatDateForClient(any());
    }


    @Test
    public void testGetTrainerSchedules_NotTrainerRole() {
        // Mock the logged-in user's role as not a trainer
        when(userService.getUserRole()).thenReturn("SOME_OTHER_ROLE");

        // Perform the method call and assert that it throws ScheduleException
        assertThrows(ScheduleException.class, () -> scheduleService.getTrainerSchedules());

        // Verify that the required method was called
        verify(userService, times(1)).getUserRole();
    }

}

