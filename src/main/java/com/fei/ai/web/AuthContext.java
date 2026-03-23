package com.fei.ai.web;

public class AuthContext {
    private final String userId;
    private final String sessionId;

    public AuthContext(String userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
