package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "mail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailId;
    private String sender;
    private String receivers;
    private Long status;
    private String subject;
    private String message;
    private Timestamp time;


//    @ManyToOne
//    @JoinColumn(name = "applicantId")
//    private ApplicantEntity applicant;

}
