package com.tweets.service.entity.ignite;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TweetIgnite implements Serializable {

    @QuerySqlField
    private String id;

    @QuerySqlField
    private String title;

    @QuerySqlField
    private String body;

    @QuerySqlField
    private String author;

    @QuerySqlField
    private List<CommentIgnite> comments;

    @QuerySqlField
    private Set<String> usersWhoLiked;

    @QuerySqlField
    private Set<String> usersWhoDisliked;

    @QuerySqlField
    private LocalDateTime date;

    public TweetIgnite() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public List<CommentIgnite> getComments() {
        return comments;
    }

    public Set<String> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public Set<String> getUsersWhoDisliked() {
        return usersWhoDisliked;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComments(List<CommentIgnite> comments) {
        this.comments = comments;
    }

    public void setUsersWhoLiked(Set<String> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public void setUsersWhoDisliked(Set<String> usersWhoDisliked) {
        this.usersWhoDisliked = usersWhoDisliked;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}