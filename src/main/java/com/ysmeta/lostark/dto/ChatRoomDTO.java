package com.ysmeta.lostark.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * 채팅방 DTO
 *
 * @author : minjooo
 * @fileName : ChatRoomDTO
 * @since : 2024/08/30
 */

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