package com.onlybuns.isa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.onlybuns.isa.dto.PostDto;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String description;
    private String imagePath; // Putanja do slike zeca

    @OneToOne
    @JoinColumn(name="location_id")
    private Location location; // Lokacija objave sa koordinatama ili adresom

    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post() {}

    public Post(PostDto postDto, User user, Location location) {
        this.user = user;  // Postavljamo korisnika iz servisa
        this.description = postDto.getDescription();
        this.imagePath = postDto.getImagePath();
        this.location = location;  // Postavljamo lokaciju iz servisa
        this.creationTime = LocalDateTime.now();  // Postavlja trenutno vreme kao vreme kreiranja
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getLikesCount() {
        return (likes != null) ? likes.size() : 0;
    }

    public int getCommentsCount() {
        return (comments != null) ? comments.size() : 0;
    }

    // NIKOLINO ZAKOMENTARISANO:

}