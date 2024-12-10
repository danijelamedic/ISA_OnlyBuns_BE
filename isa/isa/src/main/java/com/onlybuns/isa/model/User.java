package com.onlybuns.isa.model;

import com.onlybuns.isa.dto.UserDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    private Role role;
//    private String activationToken;
//    private boolean activated;

    public User() {}
    public User(UserDto user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
    }

    // ... Getters and Setters ...

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;  // Korisnik ima objave

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;  // Korisnik ima komentare

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getActivationToken() {
//        return activationToken;
//    }
//
//    public void setActivationToken(String activationToken) {
//        this.activationToken = activationToken;
//    }
//
//    public boolean isActivated() {
//        return activated;
//    }
//
//    public void setActivated(boolean activated) {
//        this.activated = activated;
//    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}


