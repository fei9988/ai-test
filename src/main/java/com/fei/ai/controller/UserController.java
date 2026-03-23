package com.fei.ai.controller;


import com.fei.ai.dto.LoginRequest;
import com.fei.ai.dto.LoginResponse;
import com.fei.ai.dto.RegisterRequest;
import com.fei.ai.entity.UserSession;
import com.fei.ai.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.fei.ai.web.ApiResponse;

/**
 * @author bytedance
 * @date 2026/3/18
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<Map<String, String>> register(@RequestBody RegisterRequest req) {
        String userId = userService.register(req.getUsername(), req.getPassword());
        return ApiResponse.ok(Map.of("userId", userId));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest req) {
        UserSession session = userService.login(req.getUsername(), req.getPassword());
        return ApiResponse.ok(new LoginResponse(session.getUserId(), session.getId()));
    }

    @PostMapping("/logout")
    public ApiResponse<Boolean> logout(@RequestHeader("X-Session-Id") String sessionId) {
        userService.logout(sessionId);
        return ApiResponse.ok(true);
    }
}
