package com.fei.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_username", columnNames = "username")
})
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 256)
    private String passwordHash;

    @Column(name = "password_salt", nullable = false, length = 64)
    private String passwordSalt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
