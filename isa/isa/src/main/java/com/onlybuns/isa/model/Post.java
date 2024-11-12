package com.onlybuns.isa.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "posting", joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName =  "id"))
    private Set<User> users = new HashSet<User>();

    private String description;
    private String imagePath; // Putanja do slike zeca

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location; // Lokacija objave sa koordinatama ili adresom

    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Post(String description, String imagePath, Location location) {
        this.description = description;
        this.imagePath = imagePath;
        this.location = location;
        this.creationTime = LocalDateTime.now();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getLikesCount() {
        return (likes != null) ? likes.size() : 0;
    }

    public int getCommentsCount() {
        return (comments != null) ? comments.size() : 0;
    }

    // NIKOLINO ZAKOMENTARISANO:

   /* public Post(String description, String imagePath, Location location) {
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
    }*/

}