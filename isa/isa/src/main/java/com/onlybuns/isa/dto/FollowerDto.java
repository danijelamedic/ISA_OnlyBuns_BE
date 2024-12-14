package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Follower;

public class FollowerDto {
    public Long id;
    public Long userId;
    public Long followedUserId;

    public FollowerDto(Long userId, Long followedUserId) {
        this.userId = userId;
        this.followedUserId = followedUserId;
    }

    public FollowerDto() {}

    public FollowerDto(Follower follower) {
        this.id = follower.getId();
        this.userId = follower.getUser().getId();
        this.followedUserId = follower.getFollowedUser().getId();
    }
}
