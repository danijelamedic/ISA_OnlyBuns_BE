package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;

public class LikeDto {
    private Long id;
    private UserDto user;
    private PostDto post;

    public LikeDto() {}

    public LikeDto(Like like){
        super();
        this.id = like.getId();
        this.user = new UserDto(like.getUser());
        this.post = new PostDto(like.getPost());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }
}
