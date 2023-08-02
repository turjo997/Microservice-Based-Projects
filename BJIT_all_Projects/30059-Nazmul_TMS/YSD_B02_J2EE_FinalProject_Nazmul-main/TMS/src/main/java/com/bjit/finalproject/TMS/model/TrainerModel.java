package com.bjit.finalproject.TMS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Trainers")
@Builder
public class TrainerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;
    private String email;
    private String fullName;
    private String designation;
    private String experience;
    private String expertise;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
}
