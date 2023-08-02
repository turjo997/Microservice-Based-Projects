package com.bjit.finalproject.TMS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Trainees")
public class TraineeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainee_id")
    private Long traineeId;
    private String email;
    private String fullName;
    private String bloodGroup;
    private String cgpa;
    private String passingYear;
    private String institute;
    private String nId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
}
