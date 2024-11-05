package com.onlybuns.isa.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private Long id;
    private String description;
    private String imagePath; // Putanja do slike zeca
    private Location location; // Lokacija objave sa koordinatama ili adresom
    private LocalDateTime creationTime;
    private int likesCount;
    private List<Comment> comments;

    public Post() {
        this.creationTime = LocalDateTime.now();
        this.likesCount = 0;
        this.comments = new ArrayList<>();
    }

    public Post(String description, String imagePath, Location location) {
        this.description = description;
        this.imagePath = imagePath;
        this.location = location;
        this.creationTime = LocalDateTime.now();
        this.likesCount = 0;
        this.comments = new ArrayList<>();
    }

    // Getters i Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", location=" + location +
                ", creationTime=" + creationTime +
                ", likesCount=" + likesCount +
                ", comments=" + comments +
                '}';
    }
}