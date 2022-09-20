package com.example.hellpyending.chat.service;

import com.example.hellpyending.chat.entity.ChatMessage;
import com.example.hellpyending.chat.entity.ChatMessageEntity;
import com.example.hellpyending.chat.entity.ChatRoom;
import com.example.hellpyending.chat.repository.ChatMessageRepository;
import com.example.hellpyending.chat.repository.ChatRoomRepositoryJPA;
import com.example.hellpyending.user.repository.UserRepository;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepositoryJPA chatRoomRepositoryJPA;
    public void createMessage(ChatMessage message) {
        Optional<Users> users = userRepository.findByUsername(message.getWriter());
        ChatRoom chatRoom = chatRoomRepositoryJPA.findByRoomId(message.getRoomId());
        ChatMessageEntity chatMessage = new ChatMessageEntity();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setUsers(users.get());
        chatMessage.setMessage(message.getMessage());
        chatMessage.setWriter(message.getWriter());
        chatMessage.setCreate(LocalDateTime.now());
        chatMessage.setUpdate(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageEntity> getMessageFromRoom(String room_id) {
        List<ChatMessageEntity> result = this.chatMessageRepository.findByChatRoom_RoomId(room_id);
        return result;
    }
}
