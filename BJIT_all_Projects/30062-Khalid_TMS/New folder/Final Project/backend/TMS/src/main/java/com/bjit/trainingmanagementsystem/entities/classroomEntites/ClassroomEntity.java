package com.bjit.trainingmanagementsystem.entities.classroomEntites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "classroom")
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ClassroomPostEntity> posts = new ArrayList<>();

    private Long batchId;    //fk
}