package com.example.hellpyending.src.chat.service;

import com.example.hellpyending.src.chat.entity.ChatRoom;
import com.example.hellpyending.src.chat.entity.ChatRoomUser;
import com.example.hellpyending.src.chat.repository.ChatRoomRepositoryJPA;
import com.example.hellpyending.src.chat.repository.ChatRoomUserRepository;
import com.example.hellpyending.src.user.repository.UserRepository;
import com.example.hellpyending.src.user.entity.Users;
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

    public ChatRoom createChatRoom(Users users,Users another_user,String name) {


        List<ChatRoomUser> chatRoomUserList = new ArrayList<>();
        name=users.getUsername()+"_"+another_user.getUsername()+"채팅방";
        ChatRoom room = ChatRoom.create(name);
        chatRoomRepository.save(room);
        ChatRoomUser chatRoomUser1 = new ChatRoomUser();

        ChatRoomUser chatRoomUser2 = new ChatRoomUser();

        chatRoomUser1.setChatRoom(room);
        chatRoomUser1.setUsers(users);
        chatRoomUserList.add(chatRoomUser1);
        chatRoomUser2.setChatRoom(room);
        chatRoomUser2.setUsers(another_user);
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

    public int IsExistRoom(Users user, Users another_user) {
        int check  = this.chatRoomUserRepository.IsExistRoom(user.getId(),another_user.getId());
        int check_2  = this.chatRoomUserRepository.IsExistRoom_2(user.getId(),another_user.getId());

        if(check!=check_2){
            return 1;
        }
        else{
            return 0;
        }
    }
}
