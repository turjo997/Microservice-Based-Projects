package com.bjit.TraineeSelection.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "circular")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Circular {
    @Id
    private Long circularId;
    private String circularName;
    private String detail;
    private LocalDate endDate;
    private String status;

}
