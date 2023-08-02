package com.bjit.finalproject.TMS.model.classroom;

import com.bjit.finalproject.TMS.model.Batch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroom_id;
    @Column(name = "title")
    private String classroomTitle;
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;
}
