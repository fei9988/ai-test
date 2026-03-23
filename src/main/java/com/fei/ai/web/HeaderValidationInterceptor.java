package com.fei.ai.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class HeaderValidationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("X-User-Id");
        String sessionId = request.getHeader("X-Session-Id");
        if (userId == null || userId.isBlank() || sessionId == null || sessionId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":\"error\",\"msg\":\"缺少请求头 X-User-Id 或 X-Session-Id\"}");
            return false;
        }
        return true;
    }
}
