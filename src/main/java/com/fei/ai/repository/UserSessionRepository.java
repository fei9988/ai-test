package com.fei.ai.repository;

import com.fei.ai.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    List<UserSession> findByUserIdAndActive(String userId, boolean active);
    Optional<UserSession> findByIdAndActive(String id, boolean active);
}
