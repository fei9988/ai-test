package com.fei.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversation_messages", indexes = {
        @Index(name = "idx_conv_messages_conv_time", columnList = "conversation_id,created_at")
})
@Getter
@Setter
public class ConversationMessage {
    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "conversation_id", nullable = false, length = 36)
    private String conversationId;

    @Column(name = "role", nullable = false, length = 16)
    private String role; // "user" or "assistant"

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) id = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = Instant.now();
    }
}
