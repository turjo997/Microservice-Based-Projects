package com.bjit.tss.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "written_marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrittenMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "written_mark_id")
    private Long writtenMarkId;
    private Float writtenMark;
    private Boolean passed;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private EvaluatorInfo evaluatorInfo;

    @OneToMany(targetEntity = WrittenQuestionMarks.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "written_mark_id", referencedColumnName = "written_mark_id")
    private List<WrittenQuestionMarks> writtenQuestionMarks;
}