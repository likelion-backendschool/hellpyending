package com.example.hellpyending.src.chat.repository;


import com.example.hellpyending.src.chat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    @Query(nativeQuery = true,value = "select count(distinct g.room_id) from chat_room_user as g where g.user_id=:userId or g.user_id=:anotherId")
    int IsExistRoom(@Param("userId") Long userId,@Param("anotherId") Long anotherId);


    @Query(nativeQuery = true,value = "select count(g.room_id) from chat_room_user as g where g.user_id=:userId or g.user_id=:anotherId")
    int IsExistRoom_2(@Param("userId") Long userId,@Param("anotherId") Long anotherId);
}
