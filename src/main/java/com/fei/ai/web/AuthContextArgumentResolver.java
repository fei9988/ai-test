package com.fei.ai.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

public class AuthContextArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(org.springframework.core.MethodParameter parameter) {
        return AuthContext.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(org.springframework.core.MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String userId = request.getHeader("X-User-Id");
        String sessionId = request.getHeader("X-Session-Id");
        if (userId == null || userId.isBlank() || sessionId == null || sessionId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "缺少请求头 X-User-Id 或 X-Session-Id");
        }
        return new AuthContext(userId, sessionId);
    }
}
