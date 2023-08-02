package com.bjit.TraineeSelection.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long markId;

    private Long applicantId;
    private Long circularId;
    private Long paperCode;
    private Long writtenMarks;
    private Long technicalMarks;
    private Long hrMarks;
    private Long status;
    private Long totalScore;



//    @OneToOne
//    @JoinColumn(name = "circularId")
//    private Circular circular;
//
//    @OneToOne
//    @JoinColumn(name = "applicantId")
//    private ApplicantEntity applicantEntity;
}
