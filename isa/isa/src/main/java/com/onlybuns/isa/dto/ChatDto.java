package com.onlybuns.isa.dto;

import java.util.List;

public class ChatDto {
    private Long id;
    private String chatKey;
    private String name;
    private String type;
    //private List<Long> participantIds;

    public ChatDto() {}

    public ChatDto(Long id, String chatKey, String name, String type) {
        this.id = id;
        this.chatKey = chatKey;
        this.name = name;
        this.type = type;
        //this.participantIds = participantIds;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   /* public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }*/
}

