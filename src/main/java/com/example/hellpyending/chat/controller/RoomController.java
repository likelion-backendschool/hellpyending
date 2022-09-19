package com.example.hellpyending.chat.controller;
import com.example.hellpyending.chat.entity.ChatMessageEntity;
import com.example.hellpyending.chat.entity.ChatRoom;
import com.example.hellpyending.chat.entity.ChatRoomDTO;
import com.example.hellpyending.chat.repository.ChatRoomRepository;
import com.example.hellpyending.chat.service.ChatMessageService;
import com.example.hellpyending.chat.service.ChatRoomService;
import com.example.hellpyending.user.service.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final ChatMessageService chatMessageService;

    //채팅방 목록 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/rooms")
    public ModelAndView rooms_db(Principal principal){

        if (principal==null) {
            ModelAndView mv = new ModelAndView("access_error");
            return mv;

        }

        Users users = this.userService.getUser(principal.getName());
        chatRoomService.findAllRoomsFromUser(users);
        ModelAndView mv = new ModelAndView("chat/rooms");
        List<ChatRoomDTO> result = new ArrayList<>();
        mv.addObject("list", chatRoomService.findAllRoomsFromUser(users));

        return mv;
    }

    //채팅방 개설
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, RedirectAttributes rttr){

        log.info("# Create Chat Room , name: " + name);
        //rttr.addFlashAttribute("roomName", chatRoomRepository.createChatRoomDTO(name));
        return "redirect:/chat/rooms";
    }



    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/room/{another_user_id}")
    public String create_db(@RequestParam String name, RedirectAttributes rttr,@PathVariable(name = "another_user_id") long another_user_id,Principal principal){

        if (principal==null) {
            return "access_error";

        }
        Users users = this.userService.getUser(principal.getName());
        ChatRoom room = chatRoomService.createChatRoom(users,another_user_id,name);
        log.info("# Create Chat Room , name: " + name);

        rttr.addFlashAttribute("roomName", chatRoomRepository.createChatRoomDTO(room));
        return "redirect:/chat/rooms";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/delete")
    public String deleteChatRoom(@RequestParam String roomId,Principal principal){

        if (principal==null) {
            return "access_error";

        }

        chatRoomService.deleteChatRoom(roomId);
        log.info("# delete Chat Room , name: " + roomId);


        return "redirect:/chat/rooms";
    }



    //채팅방 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/room")
    public void getRoom(String roomId, Model model,Principal principal){

        log.info("# get Chat Room, roomID : " + roomId);
       // model.addAttribute("room", chatRoomRepository.findRoomById(roomId));

        List<ChatMessageEntity> chatMessage = chatMessageService.getMessageFromRoom(roomId);
        model.addAttribute("room",chatRoomService.findRoomById(roomId));
        model.addAttribute("messageList",chatMessage);
    }
}