package com.bjit.trainingmanagementsystem.service.groupChat.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.chatEntities.ChatEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.groupchat.GroupChatModel;
import com.bjit.trainingmanagementsystem.models.groupchat.MessageModel;
import com.bjit.trainingmanagementsystem.repository.chat.ChatRepository;
import com.bjit.trainingmanagementsystem.service.groupChat.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupChatServiceImpl implements GroupChatService {

    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    @Override
    public GroupChatModel createGroupChat(Long batchId) {
        ChatEntity chatEntity = ChatEntity.builder()
                .batchId(batchId)
                .build();
        ChatEntity savedChatEntity = chatRepository.save(chatEntity);

        return modelMapper.map(savedChatEntity, GroupChatModel.class);
    }

    @Override
    public List<MessageModel> getAllMessages(Long chatId) {

        ChatEntity chatEntity = chatRepository.findById(chatId)
                        .orElseThrow(() -> new NotFoundException("Chat not found with ID: " + chatId));

        return chatEntity.getMessages()
                .stream()
                .map(message -> modelMapper.map(message, MessageModel.class))
                .collect(Collectors.toList());
    }

}
