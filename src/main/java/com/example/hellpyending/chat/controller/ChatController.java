package com.example.hellpyending.chat.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chat")
    public String chatGET(){
        //principal 을 통해 user 정보를 뜯어서 게시물 조회를 하던가 하자
        log.info("@ChatController, chat GET()");

        return "chat";
    }
}