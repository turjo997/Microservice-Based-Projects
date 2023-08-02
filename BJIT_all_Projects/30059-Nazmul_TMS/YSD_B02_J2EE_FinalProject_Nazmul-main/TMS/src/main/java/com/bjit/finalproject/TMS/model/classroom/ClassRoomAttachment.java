package com.bjit.finalproject.TMS.model.classroom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classroom_attachments")
public class ClassRoomAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;
    @Column(name = "message")
    private String message;
    @Column(name = "file")
    private String fileName;
    @Column(name="created_by")
    private String trainerEmail;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
//    @CreatedDate
//    @Column(name = "timestamp")
//    private Date timeStamp;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timeStamp;

    @PrePersist
    private void onCreate() {
        timeStamp = new Date();
    }
}
