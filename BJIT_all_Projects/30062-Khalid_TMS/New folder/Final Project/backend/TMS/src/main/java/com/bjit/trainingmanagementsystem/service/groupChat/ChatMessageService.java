package com.bjit.trainingmanagementsystem.service.groupChat;

import com.bjit.trainingmanagementsystem.models.groupchat.MessageModel;

public interface ChatMessageService {
    MessageModel createMessage(MessageModel messageModel);
}
