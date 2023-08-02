package com.bjit.trainingmanagementsystem.entities.roleEntites;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String fullName;
    private String profilePicturePath;
    private String contactNumber;
    private String email;
    private String gender;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}