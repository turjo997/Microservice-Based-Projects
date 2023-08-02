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
@Table(name="admitcards")
public class
AdmitcardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admitcardId;
    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApprovalEntity candidateId;
    private Long serialNumber;
}


