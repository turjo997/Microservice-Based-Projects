package com.bjit.trainingmanagementsystem.models.groupchat;

import com.bjit.trainingmanagementsystem.entities.chatEntities.ChatMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupChatModel {

    private Long batchId;
    private List<ChatMessageEntity> messages = new ArrayList<>();

}
