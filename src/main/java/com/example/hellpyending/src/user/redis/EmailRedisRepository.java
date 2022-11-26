package com.example.hellpyending.src.user.redis;

import com.example.hellpyending.src.user.entity.EmailCertificateKey;
import org.springframework.data.repository.CrudRepository;

public interface EmailRedisRepository extends CrudRepository<EmailCertificateKey, String> {

}
