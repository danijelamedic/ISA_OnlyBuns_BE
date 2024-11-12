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
    private UserDto user;
    private LocationDto location;

    public PostDto() {}

    public PostDto(Post post) {
        id = post.getId(); // Set id if needed
        description = post.getDescription();
        imagePath = post.getImagePath();
        creationTime = post.getCreationTime();
        likes = post.getLikesCount(); // Dynamically get likes count
        comments = post.getCommentsCount(); // Dynamically get comments count
        user = new UserDto(post.getUser());
        location = new LocationDto(post.getLocation());
    }

    // Getters and Setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public UserDto getUser()
    {
        return user;
    }

    public void setUser(UserDto user)
    {
        this.user = user;
    }

    public LocationDto getLocation()
    {
        return location;
    }

    public void setLocation(LocationDto location)
    {
        this.location = location;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public LocalDateTime getCreationTime() { return creationTime; }
    public void setCreationTime(LocalDateTime creationTime) { this.creationTime = creationTime; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getComments() { return comments; }
    public void setComments(int comments) { this.comments = comments; }
}
