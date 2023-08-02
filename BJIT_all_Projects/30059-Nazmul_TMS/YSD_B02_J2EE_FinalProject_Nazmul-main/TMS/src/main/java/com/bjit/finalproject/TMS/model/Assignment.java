package com.bjit.finalproject.TMS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "assignments")
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;
    @Column(name = "title")
    private String assignmentTitle;
    @Column(name = "file")
    private String fileName;
    @Column(name = "end_time")
    private String endTime;
    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
}
