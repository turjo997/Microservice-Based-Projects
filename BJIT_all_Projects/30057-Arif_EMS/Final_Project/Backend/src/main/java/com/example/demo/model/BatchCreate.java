package com.example.demo.model;

import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BatchCreate {

    private String batchName;
    private Date startDate;
    private Set<Long> traineeIds;
    private Set<Long> trainerIds;

}
