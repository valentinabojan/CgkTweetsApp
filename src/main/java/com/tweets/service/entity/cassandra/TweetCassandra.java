package com.tweets.service.entity.cassandra;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.List;

@Table(value = "tweet")
public class TweetCassandra {

    private String id;

    private String title;

    private String body;

    private String author;

//    private List<String> comments;

    @Column(value = "users_who_liked")
    private List<String> usersWhoLiked;

    @Column(value = "users_who_disliked")
    private List<String> usersWhoDisliked;

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

//    public List<String> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<String> comments) {
//        this.comments = comments;
//    }

    public List<String> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public void setUsersWhoLiked(List<String> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public List<String> getUsersWhoDisliked() {
        return usersWhoDisliked;
    }

    public void setUsersWhoDisliked(List<String> usersWhoDisliked) {
        this.usersWhoDisliked = usersWhoDisliked;
    }

    public TweetKey getPk() {
        return pk;
    }

    public void setPk(TweetKey pk) {
        this.pk = pk;
    }
}