package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.User;

public class LikeDto {
    private Long id;
    private Long userId;
    private Long postId;

    public LikeDto() {}

    public LikeDto(Like like){
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
