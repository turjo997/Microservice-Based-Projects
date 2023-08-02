package com.backend.tms.model.ScheduleBatch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleBatchReqModel {

    private Long id;
    private String courseName;
    private String courseType;
    private Date startDate;
    private Date endDate;
    private Long courseId;
    private List<Long> batchesIds;

    // Constructors, getters, and setters can be added here

}