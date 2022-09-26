package com.example.hellpyending.user.repository;

import com.example.hellpyending.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long>,UserRepositoryCustom {

    Optional<Users> findByUsername(String username);
    Optional<Users> findById(Long id);
    Optional<Users> findByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByUsername(String name);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndUsername(String email, String username);

    boolean existsByNickname(String nickname);

    Optional<Users> findByEmailOrUsernameOrNickname(String email, String name, String nickName);

    Optional<Users> findByEmailOrUsername(String email, String username);

    void deleteByUsername(String username);
}
