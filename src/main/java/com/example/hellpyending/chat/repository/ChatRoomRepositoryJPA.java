package com.example.hellpyending.chat.repository;

import com.example.hellpyending.article.domain.ArticleHashtag;
import com.example.hellpyending.chat.entity.ChatRoom;
import com.example.hellpyending.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChatRoomRepositoryJPA extends JpaRepository<ChatRoom, String> {
    ChatRoom findByRoomId(String roomId);


    @Query(nativeQuery = true,value = "select C.* from chat_room C\n" +
            "left join chat_room_user cru on C.room_id = cru.room_id\n" +
            "where cru.user_id=:id")
    List<ChatRoom> findRoomFromUserId(@Param("id")Long id);
}