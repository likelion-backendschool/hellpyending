package com.example.hellpyending.src.user;

import com.example.hellpyending.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
