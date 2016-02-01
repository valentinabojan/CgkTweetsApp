package com.tweets.service.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

public class Tweet {

    @Id
    @Field(value = "_id")
    private String id;

    @NotNull
    @NotEmpty(message = "Title is missing.")
    private String title;

    @NotNull
    @NotEmpty(message = "Body is missing.")
    private String body;
    private String author;
    private List<String> comments;
    private List<String> usersWhoLiked;
    private List<String> usersWhoDisliked;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    public Tweet() {
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

    public List<String> getComments() {
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

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void setUsersWhoLiked(List<String> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public void setUsersWhoDisliked(List<String> usersWhoDisliked) {
        this.usersWhoDisliked = usersWhoDisliked;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

        public TweetBuilder withComments(List<String> comments) {
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

        public TweetBuilder withDate(LocalDate date) {
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