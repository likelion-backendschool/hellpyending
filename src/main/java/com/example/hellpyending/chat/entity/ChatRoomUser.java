package com.example.hellpyending.chat.entity;

import com.example.hellpyending.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Users users;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    ChatRoom chatRoom;
}
