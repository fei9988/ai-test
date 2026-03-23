package com.fei.ai.service;


import com.fei.ai.entity.Conversation;
import com.fei.ai.entity.ConversationMessage;
import com.fei.ai.repository.ConversationMessageRepository;
import com.fei.ai.repository.ConversationRepository;
import com.fei.ai.web.AuthContext;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author bytedance
 * @date 2026/3/18
 **/
@Service
public class ChatService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private ConversationRepository conversationRepository;

    @Resource
    private ConversationMessageRepository messageRepository;

    public Flux<String> longChat(AuthContext auth, String message, String chatId) {
        // 获取当前用户ID和会话ID
        String userId = auth.getUserId();
        String sessionId = auth.getSessionId();

        Conversation conv = conversationRepository.findByUserIdAndChatId(userId, chatId)
                .orElseGet(() -> {
                    Conversation c = new Conversation();
                    c.setUserId(userId);
                    c.setChatId(chatId);
                    c.setTitle(null);
                    return conversationRepository.save(c);
                });

        String conversationId = userId + "_" + chatId;

        ConversationMessage userMsg = new ConversationMessage();
        userMsg.setConversationId(conv.getId());
        userMsg.setRole("user");
        userMsg.setContent(message);
        messageRepository.save(userMsg);
        conv.setUpdatedAt(Instant.now());
        if (conv.getTitle() == null || conv.getTitle().isBlank()) {
            conv.setTitle(message.length() > 30 ? message.substring(0, 30) : message);
        }
        conversationRepository.save(conv);

        AtomicReference<StringBuilder> bufRef = new AtomicReference<>(new StringBuilder());

        return chatClient.prompt()
                .user(message)
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .advisors(a->a.param("sessionId", sessionId))
                .stream().content()
                .doOnNext(chunk -> bufRef.get().append(chunk))
                .doOnComplete(() -> {
                    ConversationMessage assistantMsg = new ConversationMessage();
                    assistantMsg.setConversationId(conv.getId());
                    assistantMsg.setRole("assistant");
                    assistantMsg.setContent(bufRef.get().toString());
                    messageRepository.save(assistantMsg);
                    conv.setUpdatedAt(Instant.now());
                    conversationRepository.save(conv);
                });

    }
}
