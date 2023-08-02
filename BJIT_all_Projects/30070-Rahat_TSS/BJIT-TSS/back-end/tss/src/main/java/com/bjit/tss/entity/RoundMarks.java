package com.bjit.tss.entity;

import com.bjit.tss.enums.Round;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "round_marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Long roundId;

    @Enumerated(EnumType.STRING)
    private Round roundName;
    private Float roundMark;
    private Boolean passed;

    @ElementCollection
    @OneToMany(targetEntity = QuestionMarks.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "round_id", referencedColumnName = "round_id")
    private List<QuestionMarks> questionMarksList;
}
