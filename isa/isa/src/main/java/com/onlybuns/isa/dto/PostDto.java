package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Post;

import java.time.LocalDateTime;

public class PostDto {
    private Long id;
    private Long userId;
    private String description;
    private String imagePath;
    private LocalDateTime creationTime;
    private Long locationId;
    private int likes;
    private int comments;

    public PostDto() {}

    public PostDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.description = post.getDescription();
        this.imagePath = post.getImagePath();
        this.creationTime = post.getCreationTime();
        this.locationId = post.getLocation().getId();
        this.likes = post.getLikes().size();  // Veličina liste lajkova
        this.comments = post.getComments().size();  // Veličina liste komentara
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
