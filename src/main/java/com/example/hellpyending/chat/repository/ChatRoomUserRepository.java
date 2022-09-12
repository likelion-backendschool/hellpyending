package com.example.hellpyending.chat.repository;


import com.example.hellpyending.chat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
}
