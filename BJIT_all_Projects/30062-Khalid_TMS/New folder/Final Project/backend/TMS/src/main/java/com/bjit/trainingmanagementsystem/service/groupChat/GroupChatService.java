package com.bjit.trainingmanagementsystem.service.groupChat;

import com.bjit.trainingmanagementsystem.models.groupchat.GroupChatModel;
import com.bjit.trainingmanagementsystem.models.groupchat.MessageModel;

import java.util.List;

public interface GroupChatService {
    GroupChatModel createGroupChat(Long batchId);

    List<MessageModel> getAllMessages(Long chatId);
}
