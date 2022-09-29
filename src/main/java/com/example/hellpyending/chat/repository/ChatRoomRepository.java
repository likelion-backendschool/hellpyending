package com.example.hellpyending.chat.repository;

import com.example.hellpyending.chat.entity.ChatRoom;
import com.example.hellpyending.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
    private final ChatRoomRepositoryJPA chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserRepository userRepository;

    private Map<String, ChatRoom> chatRoomDTOMap;

    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();

    }

    public List<ChatRoom> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(result);

        return result;
    }

    public ChatRoom findRoomById(String id){
        return chatRoomDTOMap.get(id);
    }

    public ChatRoom createChatRoomDTO(ChatRoom room){
        chatRoomDTOMap.put(room.getRoomId(), room);

        return room;
    }

    public void deleteChatRoomDTO(ChatRoom room){
        chatRoomDTOMap.remove(room.getRoomId(), room);
    }
}
