package com.onlybuns.isa.dto;


import com.onlybuns.isa.model.User;

public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;

    public UserDto() {}

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.username = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
