package com.bjit.trainingmanagementsystem.models.groupchat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageModel {

    private Long messageId;
    private String message;
    private String sendingTime;

    private Long chatId;
    private Long userId;
}
