package com.example.hellpyending.chat.entity;


import com.example.hellpyending.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    ChatRoom chatRoom;


    @Column(name = "create_at", nullable = false)
    private LocalDateTime create;

    @Column(name = "update_at", nullable = false) //, nullable = false
    private LocalDateTime update;


    private String writer;

    private String message;
}
