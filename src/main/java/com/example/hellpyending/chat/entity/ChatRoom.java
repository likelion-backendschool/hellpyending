package com.example.hellpyending.chat.entity;

import com.example.hellpyending.user.entity.Users;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @Column(name = "room_id")
    private String roomId;

    private String Name;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ChatMessageEntity> ChatMessages = new ArrayList<>();

    public static ChatRoom create(String roomName){
        ChatRoom room = new ChatRoom();

        room.roomId = UUID.randomUUID().toString();
        room.Name = roomName;
        return room;
    }



}
