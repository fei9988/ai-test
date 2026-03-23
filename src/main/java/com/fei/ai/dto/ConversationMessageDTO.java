package com.fei.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ConversationMessageDTO {
    private String role;
    private String content;
    private Instant createdAt;
}
