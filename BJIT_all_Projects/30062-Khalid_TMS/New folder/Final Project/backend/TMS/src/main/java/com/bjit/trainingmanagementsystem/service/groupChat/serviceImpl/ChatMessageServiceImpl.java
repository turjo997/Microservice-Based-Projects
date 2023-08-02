package com.bjit.trainingmanagementsystem.service.groupChat.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.chatEntities.ChatEntity;
import com.bjit.trainingmanagementsystem.entities.chatEntities.ChatMessageEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.groupchat.MessageModel;
import com.bjit.trainingmanagementsystem.repository.chat.ChatMessageRepository;
import com.bjit.trainingmanagementsystem.repository.chat.ChatRepository;
import com.bjit.trainingmanagementsystem.service.groupChat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public MessageModel createMessage(MessageModel messageModel) {
        System.out.println("yo3");
        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();
        Long requestUserId = messageModel.getUserId();

        if (!authenticatedUserId.equals(requestUserId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .message(messageModel.getMessage())
                .sendingTime(messageModel.getSendingTime())
                .chatId(messageModel.getChatId())
                .userId(messageModel.getUserId())
                .build();
        ChatMessageEntity savedMessage = chatMessageRepository.save(chatMessageEntity);

        ChatEntity chatEntity = chatRepository.findById(messageModel.getChatId())
                .orElseThrow(() -> new NotFoundException("Chat not found with ID: " + messageModel.getMessageId()));
        chatEntity.getMessages().add(savedMessage);
        ChatEntity updatedChatEntity = chatRepository.save(chatEntity);

        MessageModel createdMessage = modelMapper.map(savedMessage, MessageModel.class);

        messagingTemplate.convertAndSend("/topic/chat/" + messageModel.getChatId(), createdMessage);

        return createdMessage;
    }
}
