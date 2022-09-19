package com.example.hellpyending.chat.service;

import com.example.hellpyending.chat.entity.ChatRoom;
import com.example.hellpyending.chat.entity.ChatRoomUser;
import com.example.hellpyending.chat.repository.ChatRoomRepositoryJPA;
import com.example.hellpyending.chat.repository.ChatRoomUserRepository;
import com.example.hellpyending.user.repository.UserRepository;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepositoryJPA chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserRepository userRepository;
//    public List<ChatRoomDTO> findAllRooms() {
//        return chatRoomRepository.findAllRooms();
//    }

    public List<ChatRoom> findAllRoomsFromUser(Users users) {

        return chatRoomRepository.findRoomFromUserId(users.getId());

    }

    public ChatRoom createChatRoom(Users users,long Another_user_id,String name) {
        Optional<Users> another_user = this.userRepository.findById(Another_user_id);
        List<ChatRoomUser> chatRoomUserList = new ArrayList<>();
        ChatRoom room = ChatRoom.create(name);
        chatRoomRepository.save(room);
        ChatRoomUser chatRoomUser1 = new ChatRoomUser();

        ChatRoomUser chatRoomUser2 = new ChatRoomUser();

        chatRoomUser1.setChatRoom(room);
        chatRoomUser1.setUsers(users);
        chatRoomUserList.add(chatRoomUser1);
        chatRoomUser2.setChatRoom(room);
        chatRoomUser2.setUsers(another_user.get());
        chatRoomUserList.add(chatRoomUser2);
        this.chatRoomUserRepository.saveAll(chatRoomUserList);
        return room;

    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    public void deleteChatRoom(String name) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(name);
        chatRoomRepository.delete(chatRoom);
    }
}
