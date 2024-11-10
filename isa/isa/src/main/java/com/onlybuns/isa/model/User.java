package com.onlybuns.isa.model;

import com.onlybuns.isa.dto.UserDto;
import jakarta.persistence.*;

import java.util.List;

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

    public User() {}
    public User(UserDto user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    // ... Getters and Setters ...

    @OneToMany(mappedBy = "user")
    private List<Post> posts;  // Korisnik ima objave

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
