package com.example.hellpyending.src.chat.repository;

import com.example.hellpyending.src.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
        List<ChatMessageEntity> findByChatRoom_RoomId(String room_id);

}
