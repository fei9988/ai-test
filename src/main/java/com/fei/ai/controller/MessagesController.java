package com.fei.ai.controller;

import com.fei.ai.entity.Conversation;
import com.fei.ai.entity.ConversationMessage;
import com.fei.ai.dto.SessionSummary;
import com.fei.ai.dto.ConversationMessageDTO;
import com.fei.ai.repository.ConversationMessageRepository;
import com.fei.ai.repository.ConversationRepository;
import com.fei.ai.web.ApiResponse;
import com.fei.ai.web.AuthContext;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Resource
    private ConversationRepository conversationRepository;
    @Resource
    private ConversationMessageRepository messageRepository;

    @GetMapping("/sessions")
    public ApiResponse<List<SessionSummary>> listSessions(AuthContext auth) {
        List<Conversation> list = conversationRepository.findByUserIdOrderByUpdatedAtDesc(auth.getUserId());
        List<SessionSummary> data = list.stream()
                .map(c -> new SessionSummary(c.getChatId(), c.getTitle(), c.getCreatedAt(), c.getUpdatedAt(), c.getId()))
                .collect(Collectors.toList());
        return ApiResponse.ok(data);
    }

    @GetMapping("/{chatId}")
    public ApiResponse<List<ConversationMessageDTO>> listMessages(AuthContext auth, @PathVariable String chatId) {
        Conversation conv = conversationRepository.findByUserIdAndChatId(auth.getUserId(), chatId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在"));
        List<ConversationMessage> msgs = messageRepository.findByConversationIdOrderByCreatedAtAsc(conv.getId());
        List<ConversationMessageDTO> data = msgs.stream()
                .map(m -> new ConversationMessageDTO(m.getRole(), m.getContent(), m.getCreatedAt()))
                .collect(Collectors.toList());
        return ApiResponse.ok(data);
    }
}
