package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskSubmissionModel {

    private String comment;
    private Date dateAndTime;

}
