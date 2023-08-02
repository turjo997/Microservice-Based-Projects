package com.bjit.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "validation_codes")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ValidationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validation_id")
    private Long validationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Builder.Default
    private String validationCode = generateRandomValidationCode();

    private static String generateRandomValidationCode() {
        return String.format("%05d", (int) (Math.random() * 100000));
    }
}