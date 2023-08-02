package com.bjit.trainingmanagementsystem.repository.chat;

import com.bjit.trainingmanagementsystem.entities.chatEntities.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
