package com.bjit.trainingmanagementsystem.entities.roleEntites;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import com.bjit.trainingmanagementsystem.models.auth.RegisterRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trainer")
public class TrainerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;
    private String fullName;
    private String profilePicturePath;
    private String designation;
    private String joiningDate;
    private String yearsOfExperience;
    private String expertises;
    private String contactNumber;
    private String email;
    private String presentAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
