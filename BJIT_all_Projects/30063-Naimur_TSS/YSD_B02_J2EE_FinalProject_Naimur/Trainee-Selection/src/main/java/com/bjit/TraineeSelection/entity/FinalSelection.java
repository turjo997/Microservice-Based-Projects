package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "final_selection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalSelection {
    @Id
    private String selectionId;
    private Long score;

//    @OneToOne
//    @JoinColumn(name = "circularId")
//    private Circular circular;

    @OneToOne
    @JoinColumn(name = "applicantId")
    private ApplicantEntity applicantEntity;

}
