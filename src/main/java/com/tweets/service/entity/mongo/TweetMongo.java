package com.tweets.service.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tweet")
public class TweetMongo {

    @Id
    @Field(value = "_id")
    private String id;

    private String title;
    private String body;
    private String author;
    private List<CommentMongo> comments;
    private List<String> usersWhoLiked;
    private List<String> usersWhoDisliked;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    public TweetMongo() {
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

    public List<CommentMongo> getComments() {
        return comments;
    }

    public List<String> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public List<String> getUsersWhoDisliked() {
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

    public void setComments(List<CommentMongo> comments) {
        this.comments = comments;
    }

    public void setUsersWhoLiked(List<String> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public void setUsersWhoDisliked(List<String> usersWhoDisliked) {
        this.usersWhoDisliked = usersWhoDisliked;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}