package com.fei.ai.service;

import com.fei.ai.entity.User;
import com.fei.ai.entity.UserSession;
import com.fei.ai.repository.UserRepository;
import com.fei.ai.repository.UserSessionRepository;
import com.fei.ai.util.PasswordHasher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;

    public UserService(UserRepository userRepository, UserSessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public String register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("用户名或密码不能为空");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("用户名已存在");
        }
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hash(password, salt);
        User user = new User();
        user.setUsername(username);
        user.setPasswordSalt(salt);
        user.setPasswordHash(hash);
        user = userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public UserSession login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        User user = userOpt.get();
        boolean ok = PasswordHasher.verify(password, user.getPasswordSalt(), user.getPasswordHash());
        if (!ok) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session = sessionRepository.save(session);
        return session;
    }

    @Transactional
    public void logout(String sessionId) {
        sessionRepository.findByIdAndActive(sessionId, true)
                .ifPresent(session -> {
                    session.setActive(false);
                    sessionRepository.save(session);
                });
    }
}
