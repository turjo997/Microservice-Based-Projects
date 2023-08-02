package com.bjit.finalproject.TMS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules")
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String scheduleName;
    private String startTime;
    private String endTime;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Batch> batch;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private TrainerModel trainer;
}
