package com.example.hellpyending.user.redis;

import com.example.hellpyending.user.entity.EmailCertificateKey;
import org.springframework.data.repository.CrudRepository;

public interface EmailRedisRepository extends CrudRepository<EmailCertificateKey, String> {

}
