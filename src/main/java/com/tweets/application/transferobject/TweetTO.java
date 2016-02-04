package com.tweets.application.transferobject;

import com.tweets.service.entity.Tweet;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TweetTO {

    private String id;

    private String title;

    private String body;

    private String author;

    private Integer usersWhoLikedCount;

    private Integer usersWhoDislikedCount;

    private Integer commentsCount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    public TweetTO() {}

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public TweetTO(Tweet tweet, Integer usersWhoLikedCount,
                   Integer usersWhoDislikedCount, Integer commentsCount) {
        this.id = tweet.getId();
        this.title = tweet.getTitle();
        this.body = tweet.getBody();
        this.author = tweet.getAuthor();
        this.usersWhoLikedCount = usersWhoLikedCount;
        this.usersWhoDislikedCount = usersWhoDislikedCount;
        this.date = tweet.getDate();
        this.commentsCount = commentsCount;

    }

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

    public Integer getUsersWhoLikedCount() {
        return usersWhoLikedCount;
    }

    public void setUsersWhoLikedCount(Integer usersWhoLikedCount) {
        this.usersWhoLikedCount = usersWhoLikedCount;
    }

    public Integer getUsersWhoDislikedCount() {
        return usersWhoDislikedCount;
    }

    public void setUsersWhoDislikedCount(Integer usersWhoDislikedCount) {
        this.usersWhoDislikedCount = usersWhoDislikedCount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
