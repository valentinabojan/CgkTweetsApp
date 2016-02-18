package com.tweets.service.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tweet {

    private String id;

    @NotNull
    @NotEmpty(message = "Title is missing.")
    private String title;

    @NotNull
    @NotEmpty(message = "Body is missing.")
    private String body;
    private String author;
    private List<Comment> comments;
    private Set<String> usersWhoLiked;
    private Set<String> usersWhoDisliked;
    private LocalDateTime date;

    public Tweet() {
        usersWhoDisliked = new HashSet<>();
        usersWhoLiked = new HashSet<>();
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

    public List<Comment> getComments() {
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

    public void setComments(List<Comment> comments) {
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

        public TweetBuilder withComments(List<Comment> comments) {
            tweet.comments = comments;
            return this;
        }

        public TweetBuilder withUsersWhoLiked(Set<String> usersWhoLiked) {
            tweet.usersWhoLiked = usersWhoLiked;
            return this;
        }

        public TweetBuilder withUsersWhoDisliked(Set<String> usersWhoDisliked) {
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
