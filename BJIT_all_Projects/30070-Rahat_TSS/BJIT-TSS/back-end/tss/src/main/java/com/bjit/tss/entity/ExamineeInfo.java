package com.bjit.tss.entity;

import com.bjit.tss.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "examinee_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamineeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examinee_id")
    private Long examineeId;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Date applicationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private CourseInfo courseInfo;
}