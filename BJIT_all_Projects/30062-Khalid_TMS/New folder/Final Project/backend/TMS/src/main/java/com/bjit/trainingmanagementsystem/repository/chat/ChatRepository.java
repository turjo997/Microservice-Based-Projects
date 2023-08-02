package com.bjit.trainingmanagementsystem.repository.chat;

import com.bjit.trainingmanagementsystem.entities.chatEntities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}
