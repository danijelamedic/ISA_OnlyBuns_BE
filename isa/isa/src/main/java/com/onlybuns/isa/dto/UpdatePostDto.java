package com.onlybuns.isa.dto;

public class UpdatePostDto {
    private Long id;
    private Long userId;
    private String description;
    private String imagePath;

    public UpdatePostDto() {}

    public UpdatePostDto(Long id, Long userId, String description, String imagePath) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.imagePath = imagePath;
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
}