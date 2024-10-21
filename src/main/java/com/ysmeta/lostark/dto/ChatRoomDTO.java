package com.ysmeta.lostark.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoomDTO {
    private String roomId;
    private String username;

    public static ChatRoomDTO create(String username) {
        ChatRoomDTO chatRoom = new ChatRoomDTO();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.username = username;
        return chatRoom;
    }
}