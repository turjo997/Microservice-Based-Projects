package com.ullash.springjpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "tbl_student",
        uniqueConstraints = @UniqueConstraint(
                name = "emailid_unique",
                columnNames = "email_address"
        )
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long studentId;
    private String firstName;
    private String lastName;

    @Column(
            name = "email_address",
            nullable = false
    )
    private String emailId;

    @Embedded
    private Guardian guardian;

//    @ManyToMany(
//            cascade = CascadeType.ALL ,
//            fetch = FetchType.EAGER
//    )
//    @JoinTable(
//            name = "student_course_map",
//            joinColumns = @JoinColumn(
//                    name = "student_id",
//                    referencedColumnName = "studentId"
//            ) ,
//            inverseJoinColumns = @JoinColumn(
//            name = "course_id",
//            referencedColumnName = "courseId"
//    )
//    )
//    private List<Course> courses;
//
//    public void addCourse(Course course){
//        if(courses == null) courses = new ArrayList<>();
//        courses.add(course);
//    }


}
