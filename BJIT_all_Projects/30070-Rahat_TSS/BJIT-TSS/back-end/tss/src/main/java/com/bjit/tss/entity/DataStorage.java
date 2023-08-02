package com.bjit.tss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "data_storage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Long dataId;
    private String dataKey;
    private String dataValue;
}