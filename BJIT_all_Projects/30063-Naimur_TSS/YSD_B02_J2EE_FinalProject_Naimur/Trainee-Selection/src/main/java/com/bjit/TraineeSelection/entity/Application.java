package com.bjit.TraineeSelection.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    private boolean approvalStatus;

    @ManyToOne
    @JoinColumn(name = "circularId")
    private Circular circular;

    @ManyToOne
    @JoinColumn(name = "applicantId")
    private ApplicantEntity applicantEntity;

//    @OneToOne
//    @JoinColumn(name="markId")
//    private Marks marks;



}
