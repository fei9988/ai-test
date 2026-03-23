package com.fei.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SessionSummary {
    private String chatId;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;
    private String conversationId;
}
