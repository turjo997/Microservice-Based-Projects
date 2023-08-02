package com.bjit.tss.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "hidden_code_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HiddenCodeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    @SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", initialValue = 17332)
    @Column(name = "hidden_code")
    private Long hiddenCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private CandidateMarks candidateMarks;
}