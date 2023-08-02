package com.bjit.trainingmanagementsystem.models.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentModel {

    private Long commentId;
    private String commentData;
    private String commentTime; //date and time

    private String name;
    private Long postId;   //fk
    private Long userId;   //fk
}
