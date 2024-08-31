package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.dto.ChatMessage;
import com.ysmeta.lostark.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chatting 컨트롤러
 *
 * @author : minjooo
 * @fileName : ChattingController
 * @since : 2024/08/23
 */

@Controller
@RequestMapping("/chatting")
public class ChattingController {

    private static final Logger logger = LoggerFactory.getLogger(ChattingController.class);

    private final Set<String> connectedUsers = ConcurrentHashMap.newKeySet();

    @GetMapping
    public String chatting(HttpSession session,
                           Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        model.addAttribute("username", user.getUsername());
        return "/chatting/chatting";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/sub/chatroom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        logger.info("수신된 메세지: {}", chatMessage);
        if (chatMessage.getType() == ChatMessage.MessageType.EXIT) {
            connectedUsers.remove(chatMessage.getSender());
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/sub/chatroom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        logger.info("입장한 유저: {}", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        connectedUsers.add(chatMessage.getSender());
        chatMessage.setMessage(connectedUsers.toString());

        return chatMessage;
    }

    @GetMapping("/participants")
    @SendTo("/sub/chatroom/participants")
    public Set<String> getParticipants() {
        return connectedUsers;
    }
}