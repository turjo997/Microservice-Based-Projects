package com.bjit.trainingmanagementsystem.models.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateModel {

    private String commentData;
    private String commentTime;
}
