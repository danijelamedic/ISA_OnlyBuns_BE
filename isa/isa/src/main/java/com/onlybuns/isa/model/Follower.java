package com.onlybuns.isa.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "follow_date")
    private LocalDateTime followDate;

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

    public LocalDateTime getFollowDate() {
        return followDate;
    }

    public void setFollowDate(LocalDateTime followDate) {
        this.followDate = followDate;
    }

    public Follower(User user, User followedUser) {
        this.user = user;
        this.followedUser = followedUser;
        this.followDate = LocalDateTime.now();  // postavi vreme praćenja na sada
    }
    public Follower() {
        this.followDate = LocalDateTime.now();  // postavi vreme pri kreiranju objekta
    }
}