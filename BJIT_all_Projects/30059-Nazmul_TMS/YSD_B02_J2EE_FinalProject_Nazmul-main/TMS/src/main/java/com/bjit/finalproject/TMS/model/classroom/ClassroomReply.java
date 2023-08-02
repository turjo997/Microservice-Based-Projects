package com.bjit.finalproject.TMS.model.classroom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "classroom_replies")
public class ClassroomReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;
    private String reply;
    private String email;
    private String imageFile;
    @ManyToOne
    @JoinColumn(name="classroom_attachment_id")
    private ClassRoomAttachment classRoomAttachment;
}
