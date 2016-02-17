package com.tweets.service.model;

import com.tweets.service.entity.cassandra.TweetCassandra;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.entity.mongo.TweetMongo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tweet {

    private String id;

    @NotNull
    @NotEmpty(message = "Title is missing.")
    private String title;

    @NotNull
    @NotEmpty(message = "Body is missing.")
    private String body;
    private String author;
    private List<CommentMongo> comments;
    private List<String> usersWhoLiked;
    private List<String> usersWhoDisliked;
    private LocalDateTime date;

    public Tweet(TweetMongo tweet) {
        this.id = tweet.getId();
        this.title = tweet.getTitle();
        this.body = tweet.getBody();
        this.author = tweet.getAuthor();
        this.comments = tweet.getComments();
        this.usersWhoDisliked = tweet.getUsersWhoDisliked();
        this.usersWhoLiked = tweet.getUsersWhoLiked();
        this.date = tweet.getDate();
    }

    public Tweet(TweetCassandra tweet) {
        this.id = tweet.getId();
        this.title = tweet.getTitle();
        this.body = tweet.getBody();
        this.author = tweet.getAuthor();
//        this.comments = tweet.getComments();
        this.usersWhoDisliked = tweet.getUsersWhoDisliked().stream().collect(Collectors.toList());
        this.usersWhoLiked = tweet.getUsersWhoLiked().stream().collect(Collectors.toList());;
        this.date = LocalDateTime.ofInstant(Instant.ofEpochMilli(tweet.getPk().getDate().getTime()), ZoneId.systemDefault());
    }

    public Tweet() {
        usersWhoDisliked = new ArrayList<>();
        usersWhoLiked = new ArrayList<>();
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

    public static class TweetBuilder {
        private Tweet tweet;

        private TweetBuilder() {
            tweet = new Tweet();
        }

        public TweetBuilder withId(String id) {
            tweet.id = id;
            return this;
        }

        public TweetBuilder withTitle(String title) {
            tweet.title = title;
            return this;
        }

        public TweetBuilder withBody(String body) {
            tweet.body = body;
            return this;
        }

        public TweetBuilder withAuthor(String author) {
            tweet.author = author;
            return this;
        }

        public TweetBuilder withComments(List<CommentMongo> comments) {
            tweet.comments = comments;
            return this;
        }

        public TweetBuilder withUsersWhoLiked(List<String> usersWhoLiked) {
            tweet.usersWhoLiked = usersWhoLiked;
            return this;
        }

        public TweetBuilder withUsersWhoDisliked(List<String> usersWhoDisliked) {
            tweet.usersWhoDisliked = usersWhoDisliked;
            return this;
        }

        public TweetBuilder withDate(LocalDateTime date) {
            tweet.date = date;
            return this;
        }

        public static TweetBuilder tweet() {
            return new TweetBuilder();
        }

        public Tweet build() {
            return tweet;
        }
    }
}
