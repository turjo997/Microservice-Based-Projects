package com.bjit.finalproject.TMS.model.classroom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "classroom_notices")
public class ClassroomNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String notice;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
