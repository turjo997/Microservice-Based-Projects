package com.bjit.trainingmanagementsystem.models.course;

import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseModel {

    private String courseId;
    private String courseName;
    private String description;
    private String startDate;
    private String endDate;

    private Long batchId;
    private Long trainerId;

}
