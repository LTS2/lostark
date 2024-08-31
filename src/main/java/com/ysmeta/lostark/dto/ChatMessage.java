package com.ysmeta.lostark.dto;

import lombok.Data;

/**
 * ChatMessage DTO
 *
 * @author : minjooo
 * @fileName : ChatMessage
 * @since : 2024/08/30
 */

@Data
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK, EXIT;
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}