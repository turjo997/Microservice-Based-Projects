package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.models.groupchat.MessageModel;
import com.bjit.trainingmanagementsystem.service.groupChat.ChatMessageService;
import com.bjit.trainingmanagementsystem.service.groupChat.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-chat")
@RequiredArgsConstructor
public class GroupChatController {

    private final ChatMessageService chatMessageService;
    private final GroupChatService groupChatService;

    @MessageMapping
    public ResponseEntity<MessageModel> createMessage(@RequestBody MessageModel messageModel){
        System.out.println("yo");
        return new ResponseEntity<>(chatMessageService.createMessage(messageModel), HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageModel>> getAllMessages(@PathVariable Long chatId) {
        return new ResponseEntity<>(groupChatService.getAllMessages(chatId), HttpStatus.OK);
    }
}
