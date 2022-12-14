package com.example.hellpyending.src.chat.controller;
import com.example.hellpyending.src.chat.entity.ChatMessageEntity;
import com.example.hellpyending.src.chat.entity.ChatRoom;
import com.example.hellpyending.src.chat.entity.ChatRoomDTO;
import com.example.hellpyending.src.chat.repository.ChatRoomRepository;
import com.example.hellpyending.src.chat.service.ChatMessageService;
import com.example.hellpyending.src.chat.service.ChatRoomService;
import com.example.hellpyending.src.user.service.UserService;
import com.example.hellpyending.src.user.entity.Users;
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
import java.util.Optional;

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



    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/room/{another_user_id}")
    public String create_db(RedirectAttributes rttr,@PathVariable(name = "another_user_id") long another_user_id,Principal principal){

        Optional<Users> another_user = this.userService.findById(another_user_id);
        if (principal==null) {
            return "access_error";
        }

        String name="";
        Users users = this.userService.getUser(principal.getName());
        if(this.chatRoomService.IsExistRoom(users,another_user.get()) == 1){
            return "ExistChatRoom";
        }
        ChatRoom room = chatRoomService.createChatRoom(users,another_user.get(),name);
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
        if (principal==null) {
            ModelAndView mv = new ModelAndView("access_error");


        }
        List<ChatMessageEntity> chatMessage = chatMessageService.getMessageFromRoom(roomId);
        model.addAttribute("room",chatRoomService.findRoomById(roomId));
        model.addAttribute("messageList",chatMessage);
    }
}