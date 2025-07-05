package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Type;

import java.util.List;

public class ChatDto {
    private Long id;
    private String chatKey;
    private String name;
    private Type type;
    private List<Long> participantIds;
    private Long adminId;
    public ChatDto() {}

    public ChatDto(Long id, String chatKey, String name, Type type, List<Long> participantIds, Long adminId) {
        this.id = id;
        this.chatKey = chatKey;
        this.name = name;
        this.type = type;
        this.participantIds = participantIds;
        this.adminId = adminId;
    }

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}

