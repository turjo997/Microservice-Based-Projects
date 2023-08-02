package com.bjit.trainingmanagementsystem.models.classroom;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomPostEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class ClassroomModel {

    private Long classroomId;
    private List<ClassroomPostEntity> posts = new ArrayList<>();
    private Long batchId;
}
