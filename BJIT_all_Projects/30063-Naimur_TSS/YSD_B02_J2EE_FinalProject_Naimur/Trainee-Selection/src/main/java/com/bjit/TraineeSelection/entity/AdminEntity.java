package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "userId")
    private UserEntity user;
}
