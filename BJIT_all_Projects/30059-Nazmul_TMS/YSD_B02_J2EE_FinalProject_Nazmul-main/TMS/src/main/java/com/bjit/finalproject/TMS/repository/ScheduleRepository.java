package com.bjit.finalproject.TMS.repository;

import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.Course;
import com.bjit.finalproject.TMS.model.Schedule;
import com.bjit.finalproject.TMS.model.TrainerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllScheduleByBatch(Batch batch);
    List<Schedule> findAllScheduleByTrainer(TrainerModel trainer);
    List<Schedule> findAllScheduleByCourse(Course course);

}
