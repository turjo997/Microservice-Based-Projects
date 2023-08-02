package com.bjit.tss.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "candidate_marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;
    private Float fullMark;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "examinee_id")
    private ExamineeInfo examineeInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "technical_viva_id")
    private RoundMarks technicalViva;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aptitude_test_id")
    private RoundMarks aptitudeTest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hr_viva_id")
    private RoundMarks hrViva;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "written_mark_id")
    private WrittenMarks writtenMarks;
}