package com.example.hellpyending.chat.repository;

import com.example.hellpyending.chat.entity.ChatMessageEntity;
import com.example.hellpyending.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
        List<ChatMessageEntity> findByChatRoom_RoomId(String room_id);

}
