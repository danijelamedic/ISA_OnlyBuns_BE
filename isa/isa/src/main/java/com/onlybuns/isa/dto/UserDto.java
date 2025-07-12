package com.onlybuns.isa.dto;


import com.onlybuns.isa.model.Role;
import com.onlybuns.isa.model.User;

public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Role role;
    private int postsNum;
    private int followerNum;
    private boolean activated;

    public UserDto() {}

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.postsNum = user.getPosts().size();
        this.followerNum = user.getFollowing().size();
        this.activated = user.isActivated();
    }

    public int getPostsNum() {
        return postsNum;
    }

    public void setPostsNum(int postsNum) {
        this.postsNum = postsNum;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
