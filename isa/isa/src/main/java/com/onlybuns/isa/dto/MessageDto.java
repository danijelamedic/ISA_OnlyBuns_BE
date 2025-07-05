package com.onlybuns.isa.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDto {
    private Long id;
    private Long chatId;
    private Long senderId;
    private List<Long> receiverIds;
    private String message;
    private LocalDateTime dateTime;
    private String chatKey;

    public MessageDto() {}

    public MessageDto(Long id, Long chatId, Long senderId, String message, LocalDateTime dateTime) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public List<Long> getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(List<Long> receiverIds) {
        this.receiverIds = receiverIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }
}
