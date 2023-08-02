package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskCreateModel {

    private String taskTitle;
    private String taskType;
    private Date taskDate;

}
