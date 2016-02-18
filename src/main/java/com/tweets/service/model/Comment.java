package com.tweets.service.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Comment {

    private String id;

    private String author;

    private LocalDateTime date;

    @NotNull
    @NotEmpty(message = "Body is missing.")
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public static class CommentBuilder {
        private Comment comment;

        private CommentBuilder() {
            comment = new Comment();
        }

        public CommentBuilder withId(String id) {
            comment.id = id;
            return this;
        }

        public CommentBuilder withAuthor(String author) {
            comment.author = author;
            return this;
        }

        public CommentBuilder withDate(LocalDateTime date) {
            comment.date = date;
            return this;
        }

        public CommentBuilder withBody(String body) {
            comment.body = body;
            return this;
        }

        public static CommentBuilder comment() {
            return new CommentBuilder();
        }

        public Comment build() {
            return comment;
        }
    }
}
