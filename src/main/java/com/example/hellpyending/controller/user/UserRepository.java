package com.example.hellpyending.controller.user;

import com.example.hellpyending.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
