package com.onlybuns.isa.model;

import jakarta.persistence.*;

@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "followed_user_id", nullable = false)
    private User followedUser;

    @Version
    private Integer version;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Follower(User user, User followedUser) {
        this.user = user;
        this.followedUser = followedUser;
    }
    public Follower() {}
}
