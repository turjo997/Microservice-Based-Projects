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
@Table(name="circulars")
public class CircularEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long circularId;
    private String title;
    @Column(length = 10000)
    private String description;
}
