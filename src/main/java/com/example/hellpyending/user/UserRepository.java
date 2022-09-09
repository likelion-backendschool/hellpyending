package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long>,UserRepositoryCustom {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByUsername(String name);
}
