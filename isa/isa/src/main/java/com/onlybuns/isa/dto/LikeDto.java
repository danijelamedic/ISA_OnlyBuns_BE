package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;

public class LikeDto {
    private Long id;
    private Long userId;
    private Long postId;

    public LikeDto() {}

    public LikeDto(Like like){
        super();
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

    public long getUser() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public long getPost() {
        return postId;
    }

    public void setPost(Long postId) {
        this.postId = postId;
    }
}
