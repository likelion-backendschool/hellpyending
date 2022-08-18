package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
