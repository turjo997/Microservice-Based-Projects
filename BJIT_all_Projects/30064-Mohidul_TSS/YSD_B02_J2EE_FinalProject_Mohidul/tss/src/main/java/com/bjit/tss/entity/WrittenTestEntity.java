package com.bjit.tss.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="written")
public class WrittenTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writtenId;
    private Long hiddenCode;
    private Long applicantId;
    private Double mark;
    private String circular;
}
