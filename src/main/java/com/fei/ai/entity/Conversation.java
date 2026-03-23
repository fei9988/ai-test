package com.fei.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversations", indexes = {
        @Index(name = "idx_conversations_user_chat", columnList = "user_id,chat_id", unique = true),
        @Index(name = "idx_conversations_user_updated", columnList = "user_id,updated_at")
})
@Getter
@Setter
public class Conversation {
    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "chat_id", nullable = false, length = 128)
    private String chatId;

    @Column(name = "title", length = 256)
    private String title;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) id = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = Instant.now();
        if (updatedAt == null) updatedAt = createdAt;
    }
}
