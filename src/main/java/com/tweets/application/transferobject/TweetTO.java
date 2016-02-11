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

    private Boolean liked;

    private Boolean disliked;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    public TweetTO() {}

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public TweetTO(Tweet tweet) {
        this.id = tweet.getId();
        this.title = tweet.getTitle();
        this.body = tweet.getBody();
        this.author = tweet.getAuthor();
        this.usersWhoLikedCount = tweet.getUsersWhoLiked().size();
        this.usersWhoDislikedCount = tweet.getUsersWhoDisliked().size();
        this.date = tweet.getDate();
        this.commentsCount = tweet.getComments().size();

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

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getDisliked() {
        return disliked;
    }

    public void setDisliked(Boolean disliked) {
        this.disliked = disliked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetTO tweetTO = (TweetTO) o;

        if (author != null ? !author.equals(tweetTO.author) : tweetTO.author != null) return false;
        if (body != null ? !body.equals(tweetTO.body) : tweetTO.body != null) return false;
        if (commentsCount != null ? !commentsCount.equals(tweetTO.commentsCount) : tweetTO.commentsCount != null)
            return false;
        if (date != null ? !date.equals(tweetTO.date) : tweetTO.date != null) return false;
        if (id != null ? !id.equals(tweetTO.id) : tweetTO.id != null) return false;
        if (title != null ? !title.equals(tweetTO.title) : tweetTO.title != null) return false;
        if (usersWhoDislikedCount != null ? !usersWhoDislikedCount.equals(tweetTO.usersWhoDislikedCount) : tweetTO.usersWhoDislikedCount != null)
            return false;
        if (usersWhoLikedCount != null ? !usersWhoLikedCount.equals(tweetTO.usersWhoLikedCount) : tweetTO.usersWhoLikedCount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (usersWhoLikedCount != null ? usersWhoLikedCount.hashCode() : 0);
        result = 31 * result + (usersWhoDislikedCount != null ? usersWhoDislikedCount.hashCode() : 0);
        result = 31 * result + (commentsCount != null ? commentsCount.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
