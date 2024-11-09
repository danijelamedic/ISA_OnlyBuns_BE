package com.onlybuns.isa.dto;


public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto() {}


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
