package com.tweets.service.entity.cassandra;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Set;

@Table(value = "tweet")
public class TweetCassandra {

    private String id;

    private String title;

    private String body;

    private String author;

    @Column(value = "users_who_liked")
    private Set<String> usersWhoLiked;

    @Column(value = "users_who_disliked")
    private Set<String> usersWhoDisliked;

    @PrimaryKey
    private TweetKey pk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<String> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public void setUsersWhoLiked(Set<String> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public Set<String> getUsersWhoDisliked() {
        return usersWhoDisliked;
    }

    public void setUsersWhoDisliked(Set<String> usersWhoDisliked) {
        this.usersWhoDisliked = usersWhoDisliked;
    }

    public TweetKey getPk() {
        return pk;
    }

    public void setPk(TweetKey pk) {
        this.pk = pk;
    }
}