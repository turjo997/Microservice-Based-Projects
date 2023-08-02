package com.bjit.trainingmanagementsystem.models.Roles;

import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerModel {

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

    private Long userId;
}
