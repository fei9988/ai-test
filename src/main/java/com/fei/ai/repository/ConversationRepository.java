package com.fei.ai.repository;

import com.fei.ai.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, String> {
    Optional<Conversation> findByUserIdAndChatId(String userId, String chatId);
    List<Conversation> findByUserIdOrderByUpdatedAtDesc(String userId);
}
