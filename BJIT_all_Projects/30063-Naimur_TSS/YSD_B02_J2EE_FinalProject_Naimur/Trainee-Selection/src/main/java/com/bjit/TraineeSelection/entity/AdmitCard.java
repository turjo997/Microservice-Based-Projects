package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admit_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmitCard {
    @Id
    private String admitCardId;
    private String serialNumber;

    @OneToOne
    @JoinColumn(name = "applicationId")
    private Application application;
}
