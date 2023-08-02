package com.bjit.tss.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="resource")
public class ResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rsId;
    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;
    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photoData;
    @Lob
    @Column(name = "cv", columnDefinition = "BLOB")
    private byte[] cvData;
}
