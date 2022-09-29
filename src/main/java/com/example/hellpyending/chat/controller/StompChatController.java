package com.example.hellpyending.chat.controller;

import com.example.hellpyending.alarm.entity.ArticleAlarmEntity;
import com.example.hellpyending.chat.entity.ChatMessage;
import com.example.hellpyending.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;
    private final ChatMessageService chatMessageService;
    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage message){

        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        this.chatMessageService.createMessage(message);
    }
    @MessageMapping(value = "/article/message")
    public void articleMessage(ArticleAlarmEntity message){
        // 유저 알림 방
        System.out.println("진입");

        message.setMessage(message.getCommentUserNickName() + "님이 게시글에 답글을 달았습니다.");
        template.convertAndSend("/sub/article/message/" + message.getArticleUserId(), message);
    }



}