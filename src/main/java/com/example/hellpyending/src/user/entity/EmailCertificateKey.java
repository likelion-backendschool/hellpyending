package com.example.hellpyending.src.user.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "emailCertificateKey", timeToLive = 180)
public class EmailCertificateKey {
    @Id
    private String username;
    private String certificateKey;
    private LocalDateTime createdAt;

    public EmailCertificateKey(String username, String certificateKey) {
        this.username = username;
        this.certificateKey = certificateKey;
        this.createdAt = LocalDateTime.now();
    }
}
